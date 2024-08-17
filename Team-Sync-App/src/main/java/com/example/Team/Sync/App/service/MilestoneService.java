package com.example.Team.Sync.App.service;

import java.sql.Timestamp;
import java.util.Map;

import com.example.Team.Sync.App.dao.MilestoneDAO;
import com.example.Team.Sync.App.model.Milestone;
import com.example.Team.Sync.App.model.User;

public class MilestoneService {

    private final AccessControlManagementService accessControlManagementService;
    private final MilestoneDAO milestoneDAO;

    public MilestoneService(MilestoneDAO milestoneDAO, AccessControlManagementService accessControlManagementService) {
        this.accessControlManagementService = accessControlManagementService;
        this.milestoneDAO = milestoneDAO;
    }
    
    public Milestone createMilestone(User user, String milestoneName, String status, Timestamp milestoneDate,
            Long projectId) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (accessControlManagementService.canCreateMilestone(user)) {
            Milestone milestone = new Milestone();
            milestone.setMilestone_name(milestoneName);
            milestone.setStatus(status);
            milestone.setMilestone_date(milestoneDate);
            milestone.setCreated_at(currentTime);
            milestone.setUpdated_at(currentTime);
            milestone.setProject_id(projectId);
            return milestoneDAO.saveMileStone(milestone);
        } else {
            throw new SecurityException("Access denied: User does not have permission to create project.");
        }
    }
    
     public Milestone getMilestoneById(Long milestoneId) {
        return milestoneDAO.getMilestoneById(milestoneId);
    }

    public Map<Long, Milestone> getAllMilestone() {
        return milestoneDAO.getAllMileStone();
    }

    public boolean deleteMilestone(User user, Long milestoneId) {
        if (accessControlManagementService.canDeleteMilestone(user)) {
            return milestoneDAO.deleteMilestone(milestoneId);
        } else {
            throw new SecurityException("Access denied: User does not have permission to delete milestone.");
        }
    }
}
