package com.starmediadev.plugins.starquests.objects.data;

import java.util.UUID;

public abstract class QuestData {
    protected final String questId, questObjectiveId;
    protected final UUID uniqueId;
    
    public QuestData(String questId, String questObjectiveId, UUID uniqueId) {
        this.questId = questId;
        this.questObjectiveId = questObjectiveId;
        this.uniqueId = uniqueId;
    }
    
    public String getQuestId() {
        return questId;
    }
    
    public String getQuestObjectiveId() {
        return questObjectiveId;
    }
    
    public UUID getUniqueId() {
        return uniqueId;
    }
}
