package com.example.Team.Sync.App.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.Team.Sync.App.model.Resource;

public class ResourceDao {
        private final Map<Long, Resource> resourceDataBase = new HashMap<>();
    private Long idCounter = 1L;

    public Resource save(Resource resource) {
        resource.setId(idCounter++);
        resourceDataBase.put(resource.getId(), resource);
        return resource;
    }

    public Resource findById(Long resourceId) {
        return resourceDataBase.get(resourceId);
    }

    public Map<Long, Resource> getAllResources() {
        return resourceDataBase;
    }

    public Resource update(Resource resource) {
        if (resourceDataBase.containsKey(resource.getId())) {
            resourceDataBase.put(resource.getId(), resource);
            return resource;
        }
        return null;
    }


    public Boolean delete(Long resourceId) {
        if (resourceDataBase.containsKey(resourceId)) {
            resourceDataBase.remove(resourceId);
            return true;
        }
        return false;
    }

    public List<Resource> findResourcesByTaskId(Long taskId) {
        return resourceDataBase.values().stream()
                .filter(resource -> taskId.equals(resource.getTask_id()))
                .collect(Collectors.toList());
    }

}
