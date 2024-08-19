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

    public void notifyObservers(T entity, String message) {
        for (Observer<T> observer : observers) {
            observer.update(entity, message);
        }
    }
}
