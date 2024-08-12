package com.example.Team.Sync.App.service;

import com.example.Team.Sync.App.dao.ResourceDao;
import com.example.Team.Sync.App.model.Resource;

import java.util.List;
import java.util.Map;

public class ResourceService {

    private final ResourceDao resourceDao;

    public ResourceService(ResourceDao resourceDao) {
        this.resourceDao = resourceDao;
    }

    // Create a new resource
    public Resource createResource(Resource resource) {
        return resourceDao.save(resource);
    }

    // Retrieve a resource by ID
    public Resource getResourceById(Long resourceId) {
        return resourceDao.findById(resourceId);
    }

    // Retrieve all resources
    public Map<Long, Resource> getAllResources() {
        return resourceDao.getAllResources();
    }

    // Update an existing resource
    public Resource updateResource(Resource resource) {
        return resourceDao.update(resource);
    }

    // Delete a resource by ID
    public Boolean deleteResource(Long resourceId) {
        return resourceDao.delete(resourceId);
    }

    // Retrieve all resources associated with a specific task
    public List<Resource> getResourcesByTaskId(Long taskId) {
        return resourceDao.findResourcesByTaskId(taskId);
    }
}
