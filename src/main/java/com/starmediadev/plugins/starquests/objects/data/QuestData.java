package com.starmediadev.plugins.starquests.objects.data;

import java.util.Objects;
import java.util.UUID;

public abstract class QuestData {
    protected final String questId, questObjectiveId;
    protected final UUID uniqueId;
    
    public QuestData(String questId, String questObjectiveId, UUID uniqueId) {
        this.questId = questId;
        this.questObjectiveId = questObjectiveId;
        this.uniqueId = uniqueId;
    }
    
    protected QuestData() {
        this.questId = null;
        this.questObjectiveId = null;
        this.uniqueId = null;
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        QuestData questData = (QuestData) o;
        return Objects.equals(questId, questData.questId) && Objects.equals(questObjectiveId, questData.questObjectiveId) && Objects.equals(uniqueId, questData.uniqueId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(questId, questObjectiveId, uniqueId);
    }
}
