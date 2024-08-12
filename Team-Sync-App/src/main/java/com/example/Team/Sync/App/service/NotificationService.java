package com.example.Team.Sync.App.service;

import com.example.Team.Sync.App.dao.NotificationDAO;
import com.example.Team.Sync.App.model.Notification;
import com.example.Team.Sync.App.model.Task;
import com.example.Team.Sync.App.model.User;

import java.sql.Timestamp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService{
    
     private final NotificationDAO notificationDAO;

    @Autowired
    public NotificationService(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }

    // Method to create and save a notification
    public Notification createNotification(Task task, User user, String message) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setSend_at(currentTime);
        notification.setUser_id(user.getId());
        // Save the notification to the database
        return notificationDAO.save(notification);
    }

}
