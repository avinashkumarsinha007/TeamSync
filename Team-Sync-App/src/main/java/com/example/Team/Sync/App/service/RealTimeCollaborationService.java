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

    private final TaskService taskService;
    private final ProjectService projectService;
    private final NotificationService notificationService;
    // private Map<Long, Subject<Task>> taskObserversMap = new HashMap<>();
    private Map<Long, TaskSubject> taskObserversMap = new HashMap<>();
    @Autowired
    public RealTimeCollaborationService(
            TaskService taskService,
            ProjectService projectService,
            NotificationService notificationService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.notificationService = notificationService;
    }

    // public void registerObserverForTask(Long taskId, Long userId) {
    //     Subject<Task> subject = taskObserversMap.get(taskId);
    //     if (subject == null) {
    //         subject = taskService;
    //         taskObserversMap.put(taskId, subject);
    //     }
    //     Observer<Task> observer = new UserObserver(userId, notificationService);
    //     subject.addObserver(observer);
    // }

    // public void unregisterObserverForTask(Long taskId, Long userId) {
    //     Subject<Task> subject = taskObserversMap.get(taskId);
    //     if (subject != null) {
    //         List<Observer<Task>> observers = subject.getObservers();
    //         observers.removeIf(observer -> observer.getUserId() == userId);
    //     }
    // }

    public void registerObserverForTask(Long taskId, Long userId) {
        Task task = taskService.getTaskById(taskId); // Assume this method exists in TaskService
        TaskSubject subject = taskObserversMap.computeIfAbsent(taskId, k -> new TaskSubject(task));
        Observer<Task> observer = new UserObserver(userId, notificationService);
        addObserverToTask(taskId, observer);
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

    public void addObserverToTask(Long taskId, Observer<Task> observer) {
        TaskSubject taskSubject = getTaskSubject(taskId);
        if (taskSubject != null) {
            taskSubject.addObserver(observer);
            System.out.println("Added observer: " + observer);
        } else {
            System.out.println("No TaskSubject found for task ID: " + taskId);
        }
    }

    private TaskSubject getTaskSubject(Long taskId) {
        return taskObserversMap.get(taskId);
    }
}
