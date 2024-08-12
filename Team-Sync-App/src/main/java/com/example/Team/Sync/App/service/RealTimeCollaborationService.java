package com.example.Team.Sync.App.service;

import com.example.Team.Sync.App.model.Project;
import com.example.Team.Sync.App.model.Resource;
import com.example.Team.Sync.App.model.Task;
import com.example.Team.Sync.App.model.User;
import com.example.Team.Sync.App.observer.Observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RealTimeCollaborationService {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final NotificationService notificationService;
    private final ResourceService resourceService;
    private final UserService userService;

    @Autowired
    public RealTimeCollaborationService(
        TaskService taskService, 
        ProjectService projectService, 
        NotificationService notificationService, 
        ResourceService resourceService,
        UserService userService
    ) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.notificationService = notificationService;
        this.resourceService = resourceService;
        this.userService = userService;
    }

    // Register observers for a task based on users assigned to that task
    public void registerObserversForTask(Long taskId) {
        List<Resource> resources = resourceService.getResourcesByTaskId(taskId);
        for (Resource resource : resources) {
            User user = userService.getUserById(resource.getUser_id());
            if (user != null) {
                Observer<Task> observer = createObserverForUser(user);
                taskService.addObserver(observer);
            }
        }
    }

    // Register observers for a project (if needed)
    public void registerObserversForProject(Long projectId) {
      

    }

    // Unregister observers from a task
    public void unregisterObserversFromTask(Long taskId) {
        List<Resource> resources =resourceService.getResourcesByTaskId(taskId);
        for (Resource resource : resources) {
            User user = userService.getUserById(resource.getUser_id());
            if (user != null) {
                Observer<Task>  observer = createObserverForUser(user);
                taskService.removeObserver(observer);
            }
        }
    }

    // Unregister observers from a project (if needed)
    public void unregisterObserversFromProject(Long projectId) {
       
    }

    // Method to handle adding a user as an observer to a task or project
    public void addObserverToTask(Long taskId, Observer<Task> observer) {
        Task task = taskService.getTaskById(taskId);
        if (task != null) {
            taskService.addObserver(observer);
        }
    }

    public void addObserverToProject(Long projectId, Observer<Project> observer) {
        Project project = projectService.getProjectById(projectId);
        if (project != null) {
            projectService.addObserver(observer);
        }
    }

    // Method to handle removing a user as an observer from a task or project
    public void removeObserverFromTask(Long taskId, Observer<Task> observer) {
        Task task = taskService.getTaskById(taskId);
        if (task != null) {
            taskService.removeObserver(observer);
        }
    }

    public void removeObserverFromProject(Long projectId, Observer<Project> observer) {
        Project project = projectService.getProjectById(projectId);
        if (project != null) {
            projectService.removeObserver(observer);
        }
    }

    private Observer<Task> createObserverForUser(User user) {
        return (task, message) -> {
            notificationService.createNotification(task, user, message);
        };
    }
}
