package com.example.Team.Sync.App.service;

import com.example.Team.Sync.App.model.Project;
import com.example.Team.Sync.App.model.Task;
import com.example.Team.Sync.App.model.User;
import com.example.Team.Sync.App.observer.Observer;
import com.example.Team.Sync.App.observer.Subject;
import com.example.Team.Sync.App.observer.TaskSubject;
import com.example.Team.Sync.App.observer.UserObserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RealTimeCollaborationService {
  
    private final Map<Long, TaskSubject> taskObserversMap = new HashMap<>();
    private final NotificationService notificationService;

    @Autowired
    public RealTimeCollaborationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void registerObserverForTask(Long taskId, Long userId) {
        TaskSubject subject = taskObserversMap.computeIfAbsent(taskId, k -> new TaskSubject());
        Observer<Task> observer = new UserObserver(userId, notificationService);
        subject.addObserver(observer);
        System.out.println("Observer for user " + userId + " added for task " + taskId);
    }

    public void getMapTask() {
        taskObserversMap.forEach((key, value) -> {
            System.out.println("Task ID: " + key + ", TaskSubject: " + value);
            value.getObservers().forEach(observer -> System.out.println("Observer: " + observer));
        });
    }

    public void unregisterObserverForTask(Long taskId, Long userId) {
        TaskSubject subject = taskObserversMap.get(taskId);
        if (subject != null) {
            subject.getObservers().removeIf(observer -> observer.getUserId().equals(userId));
        }
    }

    public void notifyTaskUpdate(Task updatedTask) {
        TaskSubject subject = taskObserversMap.get(updatedTask.getId());
        if (subject != null) {
            subject.updateTask(updatedTask);
        }
    }

    public void notifyTaskDeletion(Long taskId) {
        TaskSubject subject = taskObserversMap.get(taskId);
        if (subject != null) {
            subject.updateTask(null); 
        }
    }

}
