package com.starmediadev.plugins.starquests.objects;

import com.starmediadev.plugins.starquests.objects.rewards.QuestReward;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Quest extends QuestObject {
    protected Set<QuestObjective> objectives = new HashSet<>();
    protected Set<QuestReward> rewards = new HashSet<>();
    protected boolean optional;
    
    private Quest(String id) {
        super(id);
    }
    
    public void addObjective(QuestObjective objective) {
        objective.setQuestId(this.id);
        this.objectives.add(objective);
    }
    
    @Override
    public boolean isComplete(UUID player) {
        if (questManager.isQuestComplete(player, this)) {
            return true;
        }
    
        boolean allObjectivesComplete = true;
        for (QuestObjective objective : objectives) {
            if (!objective.isComplete(player)) {
                allObjectivesComplete = false;
                break;
            }
        }
        
        return allObjectivesComplete;
    }
    
    public Set<QuestObjective> getObjectives() {
        return new HashSet<>(objectives);
    }
    
    public Set<QuestReward> getRewards() {
        return new HashSet<>(rewards);
    }
    
    public boolean isOptional() {
        return optional;
    }
}
