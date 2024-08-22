package com.example.Team.Sync.App.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject<T> {
    private List<Observer<T>> observers = new ArrayList<>();

    public void addObserver(Observer<T> observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer<T> observer) {
        observers.remove(observer);
    }

    protected void notifyObservers(T data) {
        System.out.println("Notifying observers for task:" );
        for (Observer<T> observer : observers) {
            System.out.println("subject" + observer.toString() + "\n");
            observer.update(data);  
        }
    }
    
    public List<Observer<T>> getObservers() {
        return observers;
    }
}
