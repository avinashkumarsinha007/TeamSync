package com.example.Team.Sync.App.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.Team.Sync.App.model.File;

@Repository
public class FileDAO {
private final Map<Long, File> fileDatabase = new HashMap<>();
    private Long idCounter = 1L;

    // Save a new file
    public File save(File file) {
        file.setId(idCounter++);
        fileDatabase.put(file.getId(), file);
        return file;
    }

    // Find a file by its ID
    public File findById(Long fileId) {
        return fileDatabase.get(fileId);
    }

    // Get all files
    public Map<Long, File> getAllFiles() {
        return fileDatabase;
    }

    // Update an existing file
    public File update(File file) {
        if (fileDatabase.containsKey(file.getId())) {
            fileDatabase.put(file.getId(), file);
            return file;
        }
        return null;
    }

    // Delete a file by its ID
    public Boolean delete(Long fileId) {
        if (fileDatabase.containsKey(fileId)) {
            fileDatabase.remove(fileId);
            return true;
        }
        return false;
    }

     // Find files by task ID
     public Map<Long, File> findFilesByTaskId(Long taskId) {
        Map<Long, File> taskFiles = new HashMap<>();
        for (Map.Entry<Long, File> entry : fileDatabase.entrySet()) {
            if (taskId.equals(entry.getValue().getTask_id())) {
                taskFiles.put(entry.getKey(), entry.getValue());
            }
        }
        return taskFiles;
    }

    // Find files by user ID
    public Map<Long, File> findFilesByUserId(Long userId) {
        Map<Long, File> userFiles = new HashMap<>();
        for (Map.Entry<Long, File> entry : fileDatabase.entrySet()) {
            if (userId.equals(entry.getValue().getUser_id())) {
                userFiles.put(entry.getKey(), entry.getValue());
            }
        }
        return userFiles;
    }
}
