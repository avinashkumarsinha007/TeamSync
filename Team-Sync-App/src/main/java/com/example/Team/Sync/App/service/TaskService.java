package com.example.Team.Sync.App.service;

import com.example.Team.Sync.App.dao.TaskDAO;
import com.example.Team.Sync.App.factory.TaskFactory;
import com.example.Team.Sync.App.model.Task;
import com.example.Team.Sync.App.model.User;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class TaskService {
    
    private final TaskDAO taskDAO;
    private final AccessControlManagementService accessControlManagementService;
    private final RealTimeCollaborationService realTimeCollaborationService;

    public TaskService(TaskDAO taskDAO, AccessControlManagementService accessControlManagementService, RealTimeCollaborationService realTimeCollaborationService) {
        this.taskDAO = taskDAO;
        this.accessControlManagementService = accessControlManagementService;
        this.realTimeCollaborationService = realTimeCollaborationService;
    }

    public Task createTask(User user, String type, Long projectId, String taskName, String taskDescription, Long taskCreatedBy, Date dueDate) {
        if (accessControlManagementService.canCreateTask(user)) {
            Task task = TaskFactory.createTask(type, projectId, taskName, taskDescription, taskCreatedBy, dueDate);
            Task savedTask = taskDAO.save(task);
            realTimeCollaborationService.registerObserverForTask(savedTask.getId(), user.getId());
            realTimeCollaborationService.notifyTaskUpdate(savedTask); 
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
            realTimeCollaborationService.notifyTaskUpdate(updatedTask); 
            return updatedTask;
        }
        return null;
    }

    public boolean deleteTask(User user, Long taskId) {
        if (accessControlManagementService.canDeleteTask(user)) {
            boolean deletedTask = taskDAO.deleteTask(taskId);
            if (deletedTask) {
                realTimeCollaborationService.notifyTaskDeletion(taskId);
            }
            return deletedTask;
        } else {
            throw new SecurityException("Access denied: User does not have permission to delete tasks.");
        }
    }
}
