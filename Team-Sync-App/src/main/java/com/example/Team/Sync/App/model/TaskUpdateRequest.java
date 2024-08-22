package com.example.Team.Sync.App.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskUpdateRequest {
    private String taskName;
    private String taskDescription;
    private String taskStatus;
    private Date dueDate;
}