package com.example.Team.Sync.App.observer;

public interface Observer<T> {
    void update(T entity, String message);
}
