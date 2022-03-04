package com.starmediadev.plugins.starquests.objects;

import com.starmediadev.plugins.starquests.QuestManager;
import com.starmediadev.plugins.starquests.StarQuests;
import com.starmediadev.plugins.starquests.objects.actions.QuestAction;
import com.starmediadev.plugins.starquests.storage.StorageHandler;

import java.util.UUID;

public class QuestObjective {
    protected final String id, questId;
    protected String displayName, description;
    protected final QuestAction<?> questAction;
    
    public QuestObjective(String id, String questId, String displayName, String description, QuestAction<?> questAction) {
        this.id = id;
        this.questId = questId;
        this.displayName = displayName;
        this.description = description;
        this.questAction = questAction;
    }
    
    public String getId() {
        return id;
    }
    
    public String getQuestId() {
        return questId;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public QuestAction<?> getQuestAction() {
        return questAction;
    }
    
    public void complete(UUID uniqueId) {
        QuestManager questManager = StarQuests.getInstance().getQuestManager();
        StorageHandler storageHandler = questManager.getStorageHandler();
        Quest quest = questManager.getQuest(questId);
        storageHandler.setCompletedObjective(uniqueId, quest, this);
        if (quest.isComplete(uniqueId)) {
            storageHandler.setCompletedQuest(uniqueId, quest);
        }
    }
}
