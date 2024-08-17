package com.example.Team.Sync.App.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.Team.Sync.App.dao.FileDAO;
import com.example.Team.Sync.App.model.File;

public class FileManagementService {
   private final FileDAO fileDao;

    public FileManagementService(FileDAO fileDao) {
        this.fileDao = fileDao;
    }

    // Add a list of files
    public List<File> addFiles(List<File> files) {
        return files.stream()
                .map(fileDao::save)
                .collect(Collectors.toList());
    }

    // Share a file with another user by creating a copy of the file with the new user_id
    public File shareFile(Long fileId, Long newUserId) {
        File existingFile = fileDao.findById(fileId);
        if (existingFile != null) {
            File sharedFile = new File(
                    null, 
                    existingFile.getTask_id(),
                    newUserId,
                    existingFile.getFile_name(),
                    existingFile.getFile_path(),
                    existingFile.getUploading_time());
            return fileDao.save(sharedFile);
        }
        return null;
    }
    
     // Delete a file by its ID
     public Boolean deleteFile(Long fileId) {
         return fileDao.delete(fileId);
     }
    
      public File getFileById(Long fileId) {
        return fileDao.findById(fileId);
    }

    // Retrieve all files associated with a specific task ID
    public Map<Long, File> getFilesByTaskId(Long taskId) {
        return fileDao.findFilesByTaskId(taskId);
    }

    // Retrieve all files associated with a specific user ID
    public Map<Long, File> getFilesByUserId(Long userId) {
        return fileDao.findFilesByUserId(userId);
    }

     // Update an existing file
     public File updateFile(File file) {
        return fileDao.update(file);
    }
}
