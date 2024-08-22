package com.example.Team.Sync.App.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.Team.Sync.App.dao.ProjectDAO;
import com.example.Team.Sync.App.factory.ProjectFactory;
import com.example.Team.Sync.App.model.Project;
import com.example.Team.Sync.App.model.User;
import com.example.Team.Sync.App.observer.Subject;

@Service
public class ProjectService extends Subject<Project> {

    private final ProjectDAO projectDAO;
    private final AccessControlManagementService accessControlManagementService;

    public ProjectService(ProjectDAO projectDAO,AccessControlManagementService accessControlManagementService) {
        this.projectDAO = projectDAO;
        this.accessControlManagementService = accessControlManagementService;
    }

    public Project createProject(User user, String type, String projectName, String projectDescription,
            Long projectCreatedBy) {

        if (accessControlManagementService.canCreateProject(user)) {
            Project project = ProjectFactory.createProject(type, projectName, projectDescription, projectCreatedBy);
            Project savedProject = projectDAO.save(project);
            notifyObservers(savedProject);
            return savedProject;
        } else {
            throw new SecurityException("Access denied: User does not have permission to create project.");
        }
    }
    
    public Project getProjectById(Long projectId) {
        return projectDAO.findProjectById(projectId);
    }

    public Map<Long, Project> getAllProject() {
        return projectDAO.getAllProject();
    }

    public Project updateProject(User user,Long projectId, String projectName, String projectDescription) {
        if (accessControlManagementService.canCreateProject(user)) {
            Project project = projectDAO.findProjectById(projectId);
            if(project != null){
                project.setProject_name(projectName);
                project.setProject_description(projectDescription);
                Project updatedProject = projectDAO.updateProject(project);
                notifyObservers(updatedProject);
                return updatedProject;
            }
            else {
                return null;
            }
        }else {
            throw new SecurityException("Access denied: User does not have permission to create project.");
        }
    }

    public boolean deleteProject(User user, Long projectId) {
        if (accessControlManagementService.canDeleteProject(user)) {
            boolean deleted = projectDAO.deleteProject(projectId);
            if (deleted) {
                notifyObservers(null);
            }
            return deleted;
        } else {
            throw new SecurityException("Access denied: User does not have permission to delete project.");
        }
    }
}
