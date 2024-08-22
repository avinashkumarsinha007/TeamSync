package com.example.Team.Sync.App.controller;

import com.example.Team.Sync.App.model.Task;
import com.example.Team.Sync.App.model.TaskRequest;
import com.example.Team.Sync.App.model.TaskUpdateRequest;
import com.example.Team.Sync.App.model.User;
import com.example.Team.Sync.App.service.TaskService;
import com.example.Team.Sync.App.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("V1/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    // Endpoint to create a new task
    @PostMapping
    public ResponseEntity<Task> createTask(
            @RequestBody TaskRequest taskRequest,
            @RequestParam Long userId) {
        
        User user = userService.getUserById(userId);

        Task createdTask = taskService.createTask(
            user,
            taskRequest.getType(),
            taskRequest.getProjectId(),
            taskRequest.getTaskName(),
            taskRequest.getTaskDescription(),
            taskRequest.getTaskCreatedBy(),
            taskRequest.getDueDate()
        );
        return ResponseEntity.ok(createdTask);
    }

    // Endpoint to get a task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        if (task != null) {
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to get all tasks
    @GetMapping
    public ResponseEntity<Map<Long, Task>> getAllTasks() {
        Map<Long, Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    // Endpoint to update a task
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody TaskUpdateRequest taskUpdateRequest) {
        
        Task updatedTask = taskService.updateTask(
            id,
            taskUpdateRequest.getTaskName(),
            taskUpdateRequest.getTaskDescription(),
            taskUpdateRequest.getTaskStatus(),
            taskUpdateRequest.getDueDate()
        );
        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to delete a task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id,
            @RequestParam Long userId) {
        
        User user = userService.getUserById(userId);

        boolean deleted = taskService.deleteTask(user, id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


