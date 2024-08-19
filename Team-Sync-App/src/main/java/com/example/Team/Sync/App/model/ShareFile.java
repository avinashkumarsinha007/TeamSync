package com.example.Team.Sync.App.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShareFile {
    
    private Long id;
    private Long fileId;
    private Long sharedWithUserId;
    private String permission; // e.g., "read-only", "edit"
    
}
