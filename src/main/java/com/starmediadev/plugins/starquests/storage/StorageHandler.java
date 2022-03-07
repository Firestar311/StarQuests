package com.starmediadev.plugins.starquests.storage;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.actions.QuestAction;
import com.starmediadev.plugins.starquests.objects.data.QuestData;

import java.util.UUID;

public interface StorageHandler {
    void addQuestData(UUID uniqueId, QuestData value);
    
    QuestData getQuestData(UUID uniqueId, Quest quest, QuestObjective questObjective);
    
    void removeQuestData(UUID uniqueId, Quest quest, QuestObjective questObjective);
    
    void setCompletedObjective(UUID uniqueId, Quest quest, QuestObjective questObjective);
    
    void setCompletedQuest(UUID uniqueId, Quest quest);
    
    boolean isQuestComplete(UUID uniqueId, String id);
    
    boolean isQuestObjectiveComplete(UUID uniqueId, String questId, String objectiveId);
    
    void setup();
    
    void saveData();
    
    void loadData();
}
