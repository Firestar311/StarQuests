package com.starmediadev.plugins.starquests.objects.data;

import java.util.Objects;
import java.util.UUID;

/**
 * Parent class of all quest data. This is for shared traits and data
 */
public abstract class QuestData {
    /**
     * The quest id for the data that is being stored
     */
    protected final String questId;
    /**
     * The objective id for the data that is being stored
     */
    protected final String questObjectiveId;
    /**
     * The player's unique id 
     */
    protected final UUID uniqueId;
    
    /**
     * Constructs a QuestData object
     * @param questId The quest id
     * @param questObjectiveId The objective id
     * @param uniqueId The player uuid
     */
    public QuestData(String questId, String questObjectiveId, UUID uniqueId) {
        this.questId = questId;
        this.questObjectiveId = questObjectiveId;
        this.uniqueId = uniqueId;
    }
    
    /**
     * Used for loading via reflection.
     */
    protected QuestData() {
        this.questId = null;
        this.questObjectiveId = null;
        this.uniqueId = null;
    }
    
    /**
     * Gets the quest id
     * @return The quest id
     */
    public String getQuestId() {
        return questId;
    }
    
    /**
     * Gets the quest objective id
     * @return The quest objective id
     */
    public String getQuestObjectiveId() {
        return questObjectiveId;
    }
    
    /**
     * Gets the player's uuid
     * @return The player's uuid
     */
    public UUID getUniqueId() {
        return uniqueId;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        QuestData questData = (QuestData) o;
        return Objects.equals(questId, questData.questId) && Objects.equals(questObjectiveId, questData.questObjectiveId) && Objects.equals(uniqueId, questData.uniqueId);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(questId, questObjectiveId, uniqueId);
    }
}
