package com.example.Team.Sync.App.service;

import com.example.Team.Sync.App.model.Project;
import com.example.Team.Sync.App.model.Task;
import com.example.Team.Sync.App.model.User;
import com.example.Team.Sync.App.observer.Observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RealTimeCollaborationService {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final NotificationService notificationService;

    @Autowired
    public RealTimeCollaborationService(
        TaskService taskService, 
        ProjectService projectService, 
        NotificationService notificationService
    ) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.notificationService = notificationService;
    }

    // Register observers for a task based on users assigned to that task
    public void registerObserversForTask(User user) {
        if (user != null) {
            Observer<Task> observer = createObserverForUserForTaskRelatedUpdate(user);
            taskService.addObserver(observer);
        }
    }

    // Register observers for a project (if needed)
    public void registerObserversForProject(User user) {
        if (user != null) {
            Observer<Project> observer = createObserverForUserForProjectRelatedUpdate(user);
            projectService.addObserver(observer);
        }
    }

    // Unregister observers from a task
    public void unregisterObserversFromTask(User user) {
            if (user != null) {
                Observer<Task>  observer = createObserverForUserForTaskRelatedUpdate(user);
                taskService.removeObserver(observer);
            }
    }

    // Unregister observers from a project (if needed)
    public void unregisterObserversFromProject(User user) {
        if (user != null) {
            Observer<Project> observer = createObserverForUserForProjectRelatedUpdate(user);
            projectService.removeObserver(observer);
        }
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

    private Observer<Task> createObserverForUserForTaskRelatedUpdate(User user) {
        return (task, message) -> {
            notificationService.createNotification(task, user, message);
        };
    }

    private Observer<Project> createObserverForUserForProjectRelatedUpdate(User user) {
        return (project, message) -> {
            notificationService.createNotification(project, user, message);
        };
    }
}
