package com.starmediadev.plugins.starquests.storage;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.actions.QuestAction;
import com.starmediadev.plugins.starquests.objects.data.QuestData;

import java.util.UUID;

public class StorageHandler {
    public void addQuestData(UUID uniqueId, Quest quest, QuestObjective questObjective, QuestAction<?> action, QuestData value) {
        //TODO
    }
    
    public QuestData getQuestData(UUID uniqueId, Quest quest, QuestObjective questObjective) {
        return null; //TODO
    }
    
    public void removeQuestData(UUID uniqueId, Quest quest, QuestObjective questObjective) {
        //TODO
    }
    
    public void setCompletedObjective(UUID uniqueId, Quest quest, QuestObjective questObjective) {
        //TODO
        removeQuestData(uniqueId, quest, questObjective);
    }
    
    public void setCompletedQuest(UUID uniqueId, Quest quest) {
        //TODO
    }
}
