package com.example.Team.Sync.App.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    private String type;
    private Long projectId;
    private String taskName;
    private String taskDescription;
    private Long taskCreatedBy;
    private Date dueDate;
}
