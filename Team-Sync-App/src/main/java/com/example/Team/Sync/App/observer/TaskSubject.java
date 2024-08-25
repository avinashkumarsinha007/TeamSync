package com.example.Team.Sync.App.observer;

import com.example.Team.Sync.App.model.Task;

public class TaskSubject extends Subject<Task> {
    private Task task;

    public TaskSubject() {
        this.task = null; 
    }

    public void updateTask(Task updatedTask) {
        this.task = updatedTask;
        notifyObservers(updatedTask);
    }

    public Task getTask() {
        return task;
    }

    @Override
    public String toString() {
        return "TaskSubject{" +
               "task=" + task + // Ensure Task has a meaningful toString() implementation
               ", observers=" + getObservers() + // If you have observers, include them as well
               '}';
    }
}
