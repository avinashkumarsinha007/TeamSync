package com.example.Team.Sync.App.service;

import com.example.Team.Sync.App.dao.TaskDAO;
import com.example.Team.Sync.App.factory.TaskFactory;
import com.example.Team.Sync.App.model.Task;

import java.util.Date;
import java.util.Map;

public class TaskService {
    private final TaskDAO taskDAO;

    public TaskService(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public Task createTask(String type, Long projectId, String taskName, String taskDescription, Long taskCreatedBy, Date dueDate) {
        Task task = TaskFactory.createTask(type, projectId, taskName, taskDescription, taskCreatedBy, dueDate);
        return taskDAO.save(task);
    }

    public Task getTask(Long taskId) {
        return taskDAO.findTaskById(taskId);
    }

    public Map<Long, Task> getAllTasks() {
        return taskDAO.getAllTasks();
    }

    public Task updateTask(Long taskId, String taskName, String taskDescription, String taskStatus, Date dueDate) {
        Task task = taskDAO.findTaskById(taskId);
        if (task != null) {
            task.setTaskName(taskName);
            task.setTaskDescription(taskDescription);
            task.setTaskStatus(taskStatus);
            task.setDueDate(dueDate);
            return taskDAO.updateTask(task);
        }
        return null;
    }

    public boolean deleteTask(Long taskId) {
        return taskDAO.deleteTask(taskId);
    }
}
