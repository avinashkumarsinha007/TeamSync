package com.example.Team.Sync.App.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Long id;
    private Long user_id;
    private Long task_id;
    private String comment;
    private Timestamp commented_at;
}
