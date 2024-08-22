package com.example.Team.Sync.App.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.Team.Sync.App.model.ShareFile;

@Repository
public class ShareFileDAO {
 private final Map<Long, ShareFile> shareFileDatabase = new HashMap<>();
    private Long idCounter = 1L;

    // Save a new shared file record
    public ShareFile save(ShareFile shareFile) {
        shareFile.setId(idCounter++);
        shareFileDatabase.put(shareFile.getId(), shareFile);
        return shareFile;
    }

    // Find all shared records for a specific file ID
    public List<ShareFile> findByFileId(Long fileId) {
        return shareFileDatabase.values().stream()
                .filter(shareFile -> shareFile.getFileId().equals(fileId))
                .collect(Collectors.toList());
    }

    // Find all shared records for a specific user ID
    public List<ShareFile> findBySharedWithUserId(Long userId) {
        return shareFileDatabase.values().stream()
                .filter(shareFile -> shareFile.getSharedWithUserId().equals(userId))
                .collect(Collectors.toList());
    }

    // Delete a shared file record
    public Boolean delete(Long shareFileId) {
        if (shareFileDatabase.containsKey(shareFileId)) {
            shareFileDatabase.remove(shareFileId);
            return true;
        }
        return false;
    }

    // Find a specific shared file record by ID
    public ShareFile findById(Long shareFileId) {
        return shareFileDatabase.get(shareFileId);
    }

    // Optional: Update a shared file record (e.g., change permissions)
    public ShareFile update(ShareFile shareFile) {
        if (shareFileDatabase.containsKey(shareFile.getId())) {
            shareFileDatabase.put(shareFile.getId(), shareFile);
            return shareFile;
        }
        return null;
    }
}
