package com.example.Team.Sync.App.service;

import org.springframework.stereotype.Service;

import com.example.Team.Sync.App.model.Project;
import com.example.Team.Sync.App.model.Task;
import com.example.Team.Sync.App.observer.Observer;

@Service
public class RealTimeCollaborationService {
    private final TaskService taskService;
    private final ProjectService projectService;

    public RealTimeCollaborationService(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }

     public void registerTaskObserver(Observer<Task> observer) {
        taskService.addObserver(observer);
    }

    public void registerProjectObserver(Observer<Project> observer) {
        projectService.addObserver(observer);
    }

}
