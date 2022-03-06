package com.starmediadev.plugins.starquests;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestLine;
import com.starmediadev.plugins.starquests.objects.QuestObject;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.storage.StorageHandler;

import java.util.*;
import java.util.stream.Collectors;

public class QuestManager {
    
    private StorageHandler storageHandler;
    private Map<String, QuestObject> questObjects = new HashMap<>();
    
    public QuestManager(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
    }
    
    public StorageHandler getStorageHandler() {
        return storageHandler;
    }
    
    public QuestObject get(String id) {
        return questObjects.get(id);
    }
    
    public Quest getQuest(String questId) {
        if (QuestUtils.isQuestId(questId)) {
            return (Quest) questObjects.get(questId);
        }
        return null;
    }
    
    public List<Quest> getQuests() {
        return this.questObjects.values().stream().filter(value -> value instanceof Quest).map(value -> (Quest) value).collect(Collectors.toList());
    }
    
    public void add(QuestObject questObject) {
        questObjects.put(questObject.getId(), questObject);
    }
    
    public boolean isQuestComplete(UUID uuid, Quest quest) {
        return storageHandler.isQuestComplete(uuid, quest.getId());
    }
    
    public boolean isQuestObjectiveComplete(UUID uuid, Quest quest, QuestObjective objective) {
        return storageHandler.isQuestObjectiveComplete(uuid, quest.getId(), objective.getId());
    }
    
    public List<QuestLine> getQuestLines() {
        return this.questObjects.values().stream().filter(value -> value instanceof QuestLine).map(value -> (QuestLine) value).collect(Collectors.toList());
    }
}
