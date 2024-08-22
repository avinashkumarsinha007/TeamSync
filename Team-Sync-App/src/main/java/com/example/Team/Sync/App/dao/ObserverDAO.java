package com.example.Team.Sync.App.dao;

import com.example.Team.Sync.App.model.Observer;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ObserverDAO {
    private final Map<Long, Observer> observerDataBase = new HashMap<>();
    private Long idCounter = 1L;

    // Save an observer
    public Observer save(Observer observer) {
        observer.setId(idCounter++);
        observerDataBase.put(observer.getId(), observer);
        return observer;
    }

    // Find an observer by ID
    public Observer findObserverById(Long observerId) {
        return observerDataBase.get(observerId);
    }

    // Find observers by task ID
    public List<Observer> findObserversByTaskId(Long taskId) {
        return observerDataBase.values().stream()
                .filter(observer -> observer.getTask_id().equals(taskId))
                .collect(Collectors.toList());
    }

    // Get all observers
    public Map<Long, Observer> getAllObservers() {
        return observerDataBase;
    }

    // Update an observer
    public Observer updateObserver(Observer observer) {
        if (observerDataBase.containsKey(observer.getId())) {
            observerDataBase.put(observer.getId(), observer);
            return observer;
        }
        return null;
    }

    // Delete an observer by ID
    public boolean deleteObserver(Long observerId) {
        if (observerDataBase.containsKey(observerId)) {
            observerDataBase.remove(observerId);
            return true;
        }
        return false;
    }

    // Delete observers by task ID
    public void deleteObserversByTaskId(Long taskId) {
        List<Long> observerIdsToDelete = observerDataBase.values().stream()
                .filter(observer -> observer.getTask_id().equals(taskId))
                .map(Observer::getId)
                .collect(Collectors.toList());

        for (Long observerId : observerIdsToDelete) {
            observerDataBase.remove(observerId);
        }
    }
}
