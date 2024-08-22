package com.example.Team.Sync.App;

import com.example.Team.Sync.App.dao.NotificationDAO;
import com.example.Team.Sync.App.dao.ProjectDAO;
import com.example.Team.Sync.App.dao.ResourceDao;
import com.example.Team.Sync.App.dao.TaskDAO;
import com.example.Team.Sync.App.dao.UserDAO;
import com.example.Team.Sync.App.model.Resource;
import com.example.Team.Sync.App.model.Task;
import com.example.Team.Sync.App.model.User;
import com.example.Team.Sync.App.model.User.Role;
import com.example.Team.Sync.App.service.AccessControlManagementService;
import com.example.Team.Sync.App.service.NotificationService;
import com.example.Team.Sync.App.service.ProjectService;
import com.example.Team.Sync.App.service.RealTimeCollaborationService;
import com.example.Team.Sync.App.service.ResourceService;
import com.example.Team.Sync.App.service.TaskService;
import com.example.Team.Sync.App.service.UserService;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TeamSyncAppApplication {

	public static Timestamp addDaysToCurrentTime(int daysToAdd) {
        // Get current time
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        // Create a Calendar instance and set it to the current time
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTime.getTime());

        // Add the specified number of days
        calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);

        // Convert Calendar back to Timestamp
        return new Timestamp(calendar.getTimeInMillis());
    }
	public static void main(String[] args) {
		SpringApplication.run(TeamSyncAppApplication.class, args);

		TaskDAO taskDAO = new TaskDAO();
		ProjectDAO projectDAO = new ProjectDAO();
		UserDAO userDAO = new UserDAO();
		NotificationDAO notificationDAO = new NotificationDAO();
		ResourceDao resourceDao = new ResourceDao();

		UserService userService = new UserService(userDAO);
		User user = userService.createUser("Avinash", "avinash@gmail.com", "999", "aaaaaa", Role.SCRUM_MASTER , 9999999L);
		User user1 = userService.createUser("Avinash sinha", "avinash123@gmail.com", "999", "aaaaaa", Role.MANAGER , 9999999L);
		User user2 = userService.createUser("Kumar sinha", "avinash456@gmail.com", "999", "aaaaaa", Role.DEVELOPER , 9999999L);
		User user3 = userService.createUser(" sinha", "sinha@gmail.com", "999", "aaaaaa", Role.DEVELOPER, 9999999L);
		User user4 = userService.createUser("Anny", "anny@gmail.com", "999", "aaaaaa", Role.DEVELOPER, 9999999L);
		
		AccessControlManagementService accessControlManagementService = new AccessControlManagementService();
		TaskService taskService = new TaskService(taskDAO, accessControlManagementService);

		ProjectService projectService = new ProjectService(projectDAO, accessControlManagementService);
		NotificationService notificationService = new NotificationService(notificationDAO);

		RealTimeCollaborationService realTimeCollaborationService = new RealTimeCollaborationService(taskService,
				projectService, notificationService);
		ResourceService resourceService = new ResourceService(resourceDao, accessControlManagementService,
				realTimeCollaborationService);


		// Create tasks
		Task featureTask = taskService.createTask(user ,"feature", 1L, "Implement feature X", "Details about feature X", 101L, new Date());
		Task bugTask = taskService.createTask(user, "bug", 1L, "Fix bug Y", "Details about bug Y", 102L, new Date());
		System.out.println("featureTask created: " + featureTask + "\n");
		System.out.println("Bug Task created: " + bugTask + "\n");

		Resource resource = resourceService.createResource(user1, user2.getId(), true, "React", "general");
		Resource resource2 = resourceService.createResource(user1, user3.getId(), true, "React", "general");
		Resource resource3 = resourceService.createResource(user1, user4.getId(), true, "React", "general");
		System.out.println("resource " + resource + "\n");
		System.out.println("resource2 " + resource2 + "\n");
		System.out.println("resource3 " + resource3 + "\n");

		Timestamp newTime1 = addDaysToCurrentTime(6); 
		Timestamp newTime2 = addDaysToCurrentTime(8); 

		Resource resourceAdded = resourceService.addResource(user, resource.getId(), featureTask.getId(), newTime1, newTime2);
		Resource resourceAdded2 = resourceService.addResource(user, resource2.getId(), featureTask.getId(), newTime1, newTime2);
		Resource resourceAdded3 = resourceService.addResource(user, resource3.getId(), bugTask.getId(), newTime1, newTime2);
		System.out.println("resourceAdded " + resourceAdded + "\n");
		System.out.println("resourceAdded2 " + resourceAdded2 + "\n");
		System.out.println("resourceAdded3 " + resourceAdded3 + "\n");
		realTimeCollaborationService.getMapTask();
		System.out.println("taskObserversMap  "  + "\n");

		// Retrieve featureTask and update tasks
		Task task = taskService.getTaskById(featureTask.getId());
		System.out.println("Task: " + task.getTask_name() + "\n");
		Task updatedTask = taskService.updateTask(task.getId(), "Implement feature X - updated", "Updated details", "IN_PROGRESS", new Date());
		System.out.println("updatedTask: " + updatedTask + "\n");


		// Retrieve BugTask and update tasks
		Task task1 = taskService.getTaskById(bugTask.getId());
		System.out.println("Task: " + task1.getTask_name() + "\n");
		Task updatedTask1 = taskService.updateTask(task1.getId(), "Fix bug Y - updated", "Updated details about bug Y", "IN_PROGRESS", new Date());
		System.out.println("updatedTask: " + updatedTask1 + "\n");
		// Delete task
		// boolean deleted = taskService.deleteTask(user,bugTask.getId());
		// System.out.println("Task deleted: " + deleted);
	}

}
