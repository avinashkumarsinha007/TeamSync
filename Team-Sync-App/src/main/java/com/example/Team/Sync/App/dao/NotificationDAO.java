package com.example.Team.Sync.App.dao;

import com.example.Team.Sync.App.model.Notification;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class NotificationDAO {

    private final Map<Long, Notification> notificationDataBase = new HashMap<>();
    private Long idCounter = 1L;

    // Save a notification and return it with an assigned ID
    public Notification save(Notification notification) {
        notification.setId(idCounter++);
        notificationDataBase.put(notification.getId(), notification);
        return notification;
    }

    // Find a notification by its ID
    public Notification findById(Long notificationId) {
        return notificationDataBase.get(notificationId);
    }

    // Retrieve all notifications
    public Map<Long, Notification> getAllNotifications() {
        return notificationDataBase;
    }

    // Update an existing notification
    public Notification update(Notification notification) {
        if (notificationDataBase.containsKey(notification.getId())) {
            notificationDataBase.put(notification.getId(), notification);
            return notification;
        }
        return null;
    }

    // Delete a notification by its ID
    public boolean delete(Long notificationId) {
        if (notificationDataBase.containsKey(notificationId)) {
            notificationDataBase.remove(notificationId);
            return true;
        }
        return false;
    }
}
