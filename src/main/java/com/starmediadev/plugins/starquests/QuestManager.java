package com.starmediadev.plugins.starquests;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestLine;
import com.starmediadev.plugins.starquests.objects.QuestObject;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.registry.QuestLineRegistry;
import com.starmediadev.plugins.starquests.objects.registry.QuestRegistry;
import com.starmediadev.plugins.starquests.storage.StorageHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class QuestManager {
    
    private StorageHandler storageHandler;
    
    private QuestLineRegistry questLineRegistry;
    private QuestRegistry questRegistry;
    
    public QuestManager(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
        questLineRegistry = new QuestLineRegistry(this);
        questRegistry = new QuestRegistry(this);
    }
    
    public StorageHandler getStorageHandler() {
        return storageHandler;
    }
    
    public QuestObject get(String id) {
        QuestLine questLine = questLineRegistry.get(id);
        if (questLine != null) {
            return questLine;
        }
    
        return questRegistry.get(id);
    }
    
    public QuestRegistry getQuestRegistry() {
        return questRegistry;
    }
    
    public QuestLineRegistry getQuestLineRegistry() {
        return questLineRegistry;
    }
    
    public Quest getQuest(String questId) {
        return questRegistry.get(questId);
    }
    
    public List<Quest> getQuests() {
        return questRegistry.getAllRegistered();
    }
    
    public void add(QuestObject questObject) {
        if (questObject instanceof QuestLine questLine) {
            questLineRegistry.register(questLine);
        } else if (questObject instanceof Quest quest) {
            questRegistry.register(quest);
        }
    }
    
    public boolean isQuestComplete(UUID uuid, Quest quest) {
        return storageHandler.isQuestComplete(uuid, quest.getId());
    }
    
    public boolean isQuestObjectiveComplete(UUID uuid, Quest quest, QuestObjective objective) {
        return storageHandler.isQuestObjectiveComplete(uuid, quest.getId(), objective.getId());
    }
    
    public List<QuestLine> getQuestLines() {
        return questLineRegistry.getAllRegistered();
    }
}
