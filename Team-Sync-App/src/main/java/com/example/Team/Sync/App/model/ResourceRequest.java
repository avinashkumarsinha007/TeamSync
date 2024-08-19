package com.example.Team.Sync.App.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ResourceRequest {
    private Long userId;
    private Boolean availableStatus;
    private String skills;
    private String shiftWorkingIn;
    private Long taskId;
    private Long subtaskId;
    private Timestamp assignedDateStart;
    private Timestamp assignedDateEnd;
}
