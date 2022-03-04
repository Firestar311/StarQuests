package com.starmediadev.plugins.starquests.objects.data;

import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MobKillQuestData extends QuestData {
    private Map<EntityType, Integer> mobsKilled = new HashMap<>();
    
    public MobKillQuestData(String questId, String questObjectiveId, UUID uniqueId) {
        super(questId, questObjectiveId, uniqueId);
    }
    
    public void addEntityKilled(EntityType entityType) {
        mobsKilled.put(entityType, mobsKilled.getOrDefault(entityType, 0) + 1);
    }
    
    public Map<EntityType, Integer> getMobsKilled() {
        return mobsKilled;
    }
}
