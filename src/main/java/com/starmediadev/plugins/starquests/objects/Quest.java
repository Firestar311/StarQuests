package com.starmediadev.plugins.starquests.objects;

import com.starmediadev.plugins.starquests.QuestManager;
import com.starmediadev.plugins.starquests.objects.rewards.QuestReward;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Quest extends QuestObject {
    protected Set<QuestObjective> objectives = new HashSet<>();
    protected Set<QuestReward> rewards;
    protected boolean optional;
    
    private Quest(Quest.Builder builder) {
        super(builder);
        builder.objectives.forEach(this::addObjective);
        rewards = new HashSet<>(builder.rewards);
        optional = builder.optional;
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
    
    public static class Builder extends QuestObject.Builder<Quest, Quest.Builder> {
        protected Set<QuestObjective> objectives = new HashSet<>();
        protected Set<QuestReward> rewards = new HashSet<>();
        protected boolean optional;
        
        public Builder(QuestManager questManager, String id) {
            super(questManager, id);
        }
        
        public Builder optional(boolean optional) {
            this.optional = optional;
            return this;
        }
        
        public Builder addObjective(QuestObjective objective) {
            objectives.add(objective);
            return this;
        }
        
        public Builder addReward(QuestReward questReward) {
            rewards.add(questReward);
            return this;
        }
        
        public Builder clearObjectives() {
            objectives.clear();
            return this;
        }
        
        public Builder clearRewards() {
            rewards.clear();
            return this;
        }
    
        @Override
        public Quest build() {
            return new Quest(this);
        }
    }
}
