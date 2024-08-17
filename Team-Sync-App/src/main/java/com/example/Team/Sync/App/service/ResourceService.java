package com.example.Team.Sync.App.service;

import com.example.Team.Sync.App.dao.ResourceDao;
import com.example.Team.Sync.App.model.Resource;
import com.example.Team.Sync.App.model.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ResourceService {

    private final ResourceDao resourceDao;

    private final AccessControlManagementService accessControlManagementService;

    public ResourceService(ResourceDao resourceDao,
    AccessControlManagementService accessControlManagementService) {
    this.resourceDao = resourceDao;
    this.accessControlManagementService = accessControlManagementService;
    }

    public Resource createResource(User user, Long userId, Boolean availableStatus, String skills,
        String shiftWorkingIn, Long taskId, Long subtaskId, Timestamp assignedDateStart, Timestamp assignedDateEnd) {

        if (accessControlManagementService.canCreateResource(user)) {
            Resource resource = new Resource();
            resource.setUser_id(userId);
            resource.setAvailable_status(availableStatus);
            resource.setSkills(skills);
            resource.setShift_working_in(shiftWorkingIn);
            resource.setTask_id(taskId);
            resource.setSubtask_id(subtaskId);
            resource.setTask_assigned_date(assignedDateStart);
            resource.setTask_end_date(assignedDateEnd);
            return resourceDao.save(resource);
        } else {
            throw new SecurityException("Access denied: User does not have permission to create resource.");
        }
    }

    public boolean deleteResource(User user, Long resourceId) {
        if(accessControlManagementService.canCreateResource(user)){
            return resourceDao.delete(resourceId);
        } else {
            throw new SecurityException("Access denied: User does not have permission to delete resource.");
        }
    }

    // Retrieve a resource by ID
    public Resource getResourceById(Long resourceId) {
        return resourceDao.findById(resourceId);
    }

    // Retrieve all resources
    public Map<Long, Resource> getAllResources() {
        return resourceDao.getAllResources();
    }

// Update a specific field for an existing resource
public Resource updateResourceField(User user, Long resourceId, Map<String, Object> updates) {
    if (accessControlManagementService.canUpdateResource(user)) {
        // Retrieve the existing resource
        Resource resource = resourceDao.findById(resourceId);

        if (resource == null) {
            throw new IllegalArgumentException("Resource not found");
        }

        // Update only the fields provided in the updates map
        if (updates.containsKey("userId")) {
            resource.setUser_id((Long) updates.get("userId"));
        }
        if (updates.containsKey("availableStatus")) {
            resource.setAvailable_status((Boolean) updates.get("availableStatus"));
        }
        if (updates.containsKey("skills")) {
            resource.setSkills((String) updates.get("skills"));
        }
        if (updates.containsKey("shiftWorkingIn")) {
            resource.setShift_working_in((String) updates.get("shiftWorkingIn"));
        }
        if (updates.containsKey("taskId")) {
            resource.setTask_id((Long) updates.get("taskId"));
        }
        if (updates.containsKey("subtaskId")) {
            resource.setSubtask_id((Long) updates.get("subtaskId"));
        }

        if (updates.containsKey("assignedDateStart")) {
            resource.setTask_assigned_date((Timestamp) updates.get("assignedDateStart"));
        }
        if (updates.containsKey("assignedDateEnd")) {
            resource.setTask_end_date((Timestamp) updates.get("assignedDateEnd"));
        }

        // Save the updated resource
        return resourceDao.update(resource);
    } else {
        throw new SecurityException("Access denied: User does not have permission to update resource.");
    }
}

    // Add a resource to a specific task
    public Resource addResource(User user, Long resourceId, Long taskId, Timestamp assignedTaskStartTime, Timestamp endAssignedTaskTime) {
        if (accessControlManagementService.canUpdateResource(user)) {
            // Retrieve the existing resource
            Resource resource = resourceDao.findById(resourceId);

            if (resource == null) {
                throw new IllegalArgumentException("Resource not found");
            }

            // Check if the resource is available
            if (!resource.getAvailable_status()) {
                throw new IllegalArgumentException("Resource is not available.");
            }

            // Check if the resource has enough bandwidth
            if (!hasSufficientBandwidth(resource, assignedTaskStartTime,endAssignedTaskTime )) {
                throw new IllegalArgumentException("Resource does not have sufficient bandwidth.");
            }

            // Associate the resource with the given task
            resource.setTask_id(taskId);

            // Save the updated resource
            return resourceDao.update(resource);
        } else {
            throw new SecurityException("Access denied: User does not have permission to add resource to task.");
        }
    }

    // Helper method to check if the resource has sufficient bandwidth
    private boolean hasSufficientBandwidth(Resource resource , Timestamp assignedTaskStartTime,  Timestamp endAssignedTaskTime) {
        // Example logic: Check how many tasks the resource is currently assigned to

        if (resource.getTask_assigned_date().before(assignedTaskStartTime) && 
            resource.getTask_end_date().before(assignedTaskStartTime)|| resource.getTask_assigned_date().after(endAssignedTaskTime) && 
            resource.getTask_assigned_date().after(assignedTaskStartTime) ) {
            return true;
        }

        return false;
    }

        // Remove a resource from a specific task
    public Resource removeResource(User user, Long resourceId, Long taskId) {
        if (accessControlManagementService.canUpdateResource(user)) {
            // Retrieve the existing resource
            Resource resource = resourceDao.findById(resourceId);

            if (resource == null) {
                throw new IllegalArgumentException("Resource not found");
            }

            // Check if the resource is associated with the specified task
            if (taskId.equals(resource.getTask_id())) {
                // Disassociate the resource from the task
                resource.setTask_id(null);

                // Save the updated resource
                return resourceDao.update(resource);
            } else {
                throw new IllegalArgumentException("Resource is not associated with the specified task.");
            }
        } else {
            throw new SecurityException("Access denied: User does not have permission to remove resource from task.");
        }
    }

    // Retrieve all resources associated with a specific task
    public List<Resource> getResourcesByTaskId(Long taskId) {
        return resourceDao.findResourcesByTaskId(taskId);
    }
}
