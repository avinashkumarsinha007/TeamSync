package com.example.Team.Sync.App.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.Team.Sync.App.model.Milestone;

@Repository
public class MilestoneDAO {
    private final Map<Long, Milestone> mileStoneData = new HashMap<>();

    private Long idCounter = 1L;

    public Milestone saveMileStone(Milestone milestone) {
        milestone.setId(idCounter++);
        mileStoneData.put(milestone.getId(), milestone);
        return milestone;
    }

    public Milestone getMilestoneById(Long mileStoneId) {
        return mileStoneData.get(mileStoneId);
    }

    public Map<Long, Milestone> getAllMileStone() {
        return mileStoneData;
    }

    public Milestone updateMilestone(Milestone milestone) {
        if (mileStoneData.containsKey(milestone.getId())) {
            mileStoneData.put(milestone.getId(), milestone);
            return milestone;
        }
        return null;
    }
    
    public boolean deleteMilestone(Long mileStoneId) {
        if (mileStoneData.containsKey(mileStoneId)) {
            mileStoneData.remove(mileStoneId);
            return true;
        }
        return false;
    }
}
