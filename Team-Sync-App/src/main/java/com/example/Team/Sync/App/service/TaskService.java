package com.example.Team.Sync.App.service;

import com.example.Team.Sync.App.dao.TaskDAO;
import com.example.Team.Sync.App.factory.TaskFactory;
import com.example.Team.Sync.App.model.Task;
import com.example.Team.Sync.App.model.User;
import com.example.Team.Sync.App.observer.Subject;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class TaskService extends Subject<Task>{
    
    private final TaskDAO taskDAO;
    private final AccessControlManagementService accessControlManagementService;

    public TaskService(TaskDAO taskDAO, AccessControlManagementService accessControlManagementService) {
        this.taskDAO = taskDAO;
        this.accessControlManagementService = accessControlManagementService;
    }

    public Task createTask(User user, String type, Long projectId, String taskName, String taskDescription, Long taskCreatedBy, Date dueDate) {
        if (accessControlManagementService.canCreateTask(user)) {
            Task task = TaskFactory.createTask(type, projectId, taskName, taskDescription, taskCreatedBy, dueDate);
            Task savedTask = taskDAO.save(task);
            notifyObservers(savedTask);
            return savedTask;
        } else {
            throw new SecurityException("Access denied: User does not have permission to create tasks.");
        }
    }

    public Task getTaskById(Long taskId) {
        return taskDAO.findTaskById(taskId);
    }

    public Map<Long, Task> getAllTasks() {
        return taskDAO.getAllTask();
    }

    public Task updateTask(Long taskId, String taskName, String taskDescription, String taskStatus, Date dueDate) {
        Task task = taskDAO.findTaskById(taskId);
        if (task != null) {
            task.setTask_name(taskName);
            task.setTask_description(taskDescription);
            task.setTask_status(taskStatus);
            task.setDue_date(dueDate);
            Task updatedTask = taskDAO.updateTask(task);
            System.out.println("Notifying observers of task update...");
            System.out.println("Updating task: " + updatedTask + "\n");
            System.out.println("Observers before notifying: " + getObservers() + "\n");
            notifyObservers(updatedTask);
            return updatedTask;
        }
        return null;
    }

    public boolean deleteTask(User user, Long taskId) {
        if (accessControlManagementService.canDeleteTask(user)) {
            boolean deletedTask = taskDAO.deleteTask(taskId);
            if (deletedTask) {
                notifyObservers(null);
            }
            return deletedTask;
        } else {
            throw new SecurityException("Access denied: User does not have permission to delete tasks.");
        }
    }
}
