package com.example.Team.Sync.App.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Observer<T> {
    private Long id;
    private Long task_id;
    private String notification_type;
    private String subscription_status;
}
