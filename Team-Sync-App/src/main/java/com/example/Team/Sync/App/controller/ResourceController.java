package com.example.Team.Sync.App.controller;

import com.example.Team.Sync.App.model.Resource;
import com.example.Team.Sync.App.model.ResourceRequest;
import com.example.Team.Sync.App.model.User;
import com.example.Team.Sync.App.service.ResourceService;
import com.example.Team.Sync.App.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1/api/resources")
public class ResourceController {

    private final ResourceService resourceService;
    private final UserService userService;

    @Autowired
    public ResourceController(ResourceService resourceService, UserService userService) {
        this.resourceService = resourceService;
        this.userService = userService;
    }

    // Endpoint to create a new resource
    @PostMapping
    public ResponseEntity<Resource> createResource(
            @RequestParam Long userId,
            @RequestBody ResourceRequest resourceRequest) {

        User user = userService.getUserById(userId);
        Resource createdResource = resourceService.createResource(
                user,
                resourceRequest.getUserId(),
                resourceRequest.getAvailableStatus(),
                resourceRequest.getSkills(),
                resourceRequest.getShiftWorkingIn()
        );
        return ResponseEntity.ok(createdResource);
    }

    // Endpoint to update specific fields of an existing resource
    @PatchMapping("/{resourceId}")
    public ResponseEntity<Resource> updateResourceField(
            @RequestParam Long userId,
            @PathVariable Long resourceId,
            @RequestBody Map<String, Object> updates) {

        User user = userService.getUserById(userId);
        Resource updatedResource = resourceService.updateResourceField(user, resourceId, updates);
        return ResponseEntity.ok(updatedResource);
    }

    // Endpoint to delete a resource
    @DeleteMapping("/{resourceId}")
    public ResponseEntity<Void> deleteResource(
            @RequestParam Long userId,
            @PathVariable Long resourceId) {

        User user = userService.getUserById(userId);
        boolean deleted = resourceService.deleteResource(user, resourceId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Endpoint to add a resource to a specific task
    @PostMapping("/{resourceId}/assign")
    public ResponseEntity<Resource> addResourceToTask(
            @RequestParam Long userId,
            @PathVariable Long resourceId,
            @RequestParam Long taskId,
            @RequestParam Timestamp assignedTaskStartTime,
            @RequestParam Timestamp endAssignedTaskTime) {

        User user = userService.getUserById(userId);
        Resource updatedResource = resourceService.addResource(user, resourceId, taskId, assignedTaskStartTime, endAssignedTaskTime);
        return ResponseEntity.ok(updatedResource);
    }

    // Endpoint to remove a resource from a specific task
    @DeleteMapping("/{resourceId}/remove/{taskId}")
    public ResponseEntity<Resource> removeResourceFromTask(
            @RequestParam Long userId,
            @PathVariable Long resourceId,
            @PathVariable Long taskId) {

        User user = userService.getUserById(userId);
        Resource updatedResource = resourceService.removeResource(user, resourceId, taskId);
        return ResponseEntity.ok(updatedResource);
    }

    // Endpoint to get a resource by ID
    @GetMapping("/{resourceId}")
    public ResponseEntity<Resource> getResourceById(
            @PathVariable Long resourceId) {

        Resource resource = resourceService.getResourceById(resourceId);
        return resource != null ? ResponseEntity.ok(resource) : ResponseEntity.notFound().build();
    }

    // Endpoint to get all resources
    @GetMapping
    public ResponseEntity<Map<Long, Resource>> getAllResources() {
        return ResponseEntity.ok(resourceService.getAllResources());
    }

    // Endpoint to get resources associated with a specific task
    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Resource>> getResourcesByTaskId(
            @PathVariable Long taskId) {

        List<Resource> resources = resourceService.getResourcesByTaskId(taskId);
        return ResponseEntity.ok(resources);
    }
}
