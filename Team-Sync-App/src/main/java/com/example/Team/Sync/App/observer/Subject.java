package com.example.Team.Sync.App.observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Subject<T> {
   private final List<Observer<T>> observers = Collections.synchronizedList(new ArrayList<>());

    public void addObserver(Observer<T> observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer<T> observer) {
        observers.remove(observer);
    }

    protected void notifyObservers(T data) {
        for (Observer<T> observer : observers) {
            observer.update(data);  
        }
    }
    
    public List<Observer<T>> getObservers() {
        return observers;
    }
}
