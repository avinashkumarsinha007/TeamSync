package com.example.Team.Sync.App.observer;

import com.example.Team.Sync.App.model.Task;

public class TaskSubject extends Subject<Task> {
    private Task task;
     
    public TaskSubject(Task task) {
        this.task = task;
    }

    public void updateTask(Task updatedTask) {
        this.task = updatedTask;
        notifyObservers(updatedTask);
    }

    @Override
    protected void notifyObservers(Task updatedTask) {
        System.out.println("Notifying observers for task: " + updatedTask.getTask_name());
        for (Observer<Task> observer : getObservers()) {
            System.out.println("Notifying observer for user: " + observer.toString());
            observer.update(updatedTask);
        }
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
