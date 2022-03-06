package com.starmediadev.plugins.starquests.objects;

import com.starmediadev.plugins.starquests.QuestManager;
import com.starmediadev.plugins.starquests.objects.rewards.QuestReward;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Quest extends QuestObject {
    protected Set<QuestObjective> objectives = new HashSet<>();
    protected Set<QuestReward> rewards = new HashSet<>();
    
    public Quest(QuestManager questManager, String id, String displayName, String description, boolean repeatable) {
        super(questManager, id, displayName, description, repeatable);
    }
    
    @Override
    public boolean isComplete(UUID player) {
        return false;
    }
    
    public Set<QuestObjective> getObjectives() {
        return new HashSet<>(objectives);
    }
    
    public Set<QuestReward> getRewards() {
        return new HashSet<>(rewards);
    }
}
