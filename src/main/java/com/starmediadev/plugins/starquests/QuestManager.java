package com.starmediadev.plugins.starquests;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestLine;
import com.starmediadev.plugins.starquests.objects.QuestObject;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.registry.QuestLineRegistry;
import com.starmediadev.plugins.starquests.objects.registry.QuestObjectiveRegistry;
import com.starmediadev.plugins.starquests.objects.registry.QuestRegistry;
import com.starmediadev.plugins.starquests.objects.registry.QuestRewardRegistry;
import com.starmediadev.plugins.starquests.objects.rewards.QuestReward;
import com.starmediadev.plugins.starquests.storage.StorageHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QuestManager {
    
    private StorageHandler storageHandler;
    
    private QuestLineRegistry questLineRegistry;
    private QuestRegistry questRegistry;
    private QuestObjectiveRegistry objectiveRegistry;
    private QuestRewardRegistry rewardRegistry;
    
    public QuestManager(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
        questLineRegistry = new QuestLineRegistry(this);
        questRegistry = new QuestRegistry(this);
        objectiveRegistry = new QuestObjectiveRegistry(this);
        rewardRegistry = new QuestRewardRegistry(this);
    }
    
    public StorageHandler getStorageHandler() {
        return storageHandler;
    }
    
    public QuestObject get(String id) {
        QuestLine questLine = questLineRegistry.get(id);
        if (questLine != null) {
            return questLine;
        }
    
        Quest quest = questRegistry.get(id);
        if (quest != null) {
            return quest;
        }
    
        return objectiveRegistry.get(id);
    }
    
    public QuestRegistry getQuestRegistry() {
        return questRegistry;
    }
    
    public QuestLineRegistry getQuestLineRegistry() {
        return questLineRegistry;
    }
    
    public QuestRewardRegistry getRewardRegistry() {
        return rewardRegistry;
    }
    
    public QuestObjectiveRegistry getObjectiveRegistry() {
        return objectiveRegistry;
    }
    
    public Quest getQuest(String questId) {
        return questRegistry.get(questId);
    }
    
    public List<Quest> getQuests() {
        return questRegistry.getAllRegistered();
    }
    
    public void register(Object object) {
        if (object instanceof QuestLine questLine) {
            questLineRegistry.register(questLine);
        } else if (object instanceof Quest quest) {
            questRegistry.register(quest);
        } else if (object instanceof QuestObjective questObjective) {
            objectiveRegistry.register(questObjective);
        } else if (object instanceof QuestReward questReward) {
            rewardRegistry.register(questReward);
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
    
    public boolean isQuestLineComplete(UUID player, QuestLine questLine) {
        return storageHandler.isQuestLineComplete(player, questLine);
    }
    
    public List<String> getAllIds() {
        List<String> ids = new ArrayList<>();
        getQuestLineRegistry().getAllRegistered().forEach(object -> ids.add(object.getId()));
        getQuestRegistry().getAllRegistered().forEach(object -> ids.add(object.getId()));
        getObjectiveRegistry().getAllRegistered().forEach(object -> ids.add(object.getId()));
        getRewardRegistry().getAllRegistered().forEach(object -> ids.add(object.getId())); 
        return ids;
    }
}
