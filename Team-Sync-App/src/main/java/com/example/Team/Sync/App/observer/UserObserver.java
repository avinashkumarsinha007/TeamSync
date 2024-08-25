package com.example.Team.Sync.App.observer;

import com.example.Team.Sync.App.model.Task;
import com.example.Team.Sync.App.service.NotificationService;

public class UserObserver implements Observer<Task> {

    private Long userId;
    private NotificationService notificationService;

    public UserObserver(Long userId, NotificationService notificationService) {
        this.userId = userId;
        this.notificationService = notificationService;
    }

    @Override
    public void update(Task task) {
        String message = "Task " + task.getTask_name() + "";
        System.out.println("Updating observer for user " + userId + ": " + message + "\n");
        notificationService.createNotification(userId, message);
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "UserObserver{" +
               "userId=" + userId +
               '}';
    }
}
