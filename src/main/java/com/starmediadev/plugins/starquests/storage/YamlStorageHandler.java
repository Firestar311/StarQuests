package com.starmediadev.plugins.starquests.storage;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.data.QuestData;
import com.starmediadev.utils.collection.ListMap;
import com.starmediadev.utils.collection.MultiMap;

import java.util.UUID;

public class YamlStorageHandler implements StorageHandler {
    
    private ListMap<UUID, QuestData> questDataMap = new ListMap<>();
    private ListMap<UUID, String> completedQuests = new ListMap<>();
    private MultiMap<UUID, String, String> completedQuestObjectives = new MultiMap<>();
    
    @Override
    public void addQuestData(UUID uniqueId, QuestData value) {
        questDataMap.add(uniqueId, value);
    }
    
    @Override
    public QuestData getQuestData(UUID uniqueId, Quest quest, QuestObjective questObjective) {
        if (questDataMap.containsKey(uniqueId)) {
            for (QuestData questData : questDataMap.get(uniqueId)) {
                if (questData.getQuestId().equals(quest.getId()) && questData.getQuestObjectiveId().equals(questObjective.getId())) {
                    return questData;
                }
            }
        }
        return null;
    }
    
    @Override
    public void removeQuestData(UUID uniqueId, Quest quest, QuestObjective questObjective) {
        if (questDataMap.containsKey(uniqueId)) {
            questDataMap.get(uniqueId).removeIf(questData -> questData.getQuestId().equals(quest.getId()) && questData.getQuestObjectiveId().equals(questObjective.getId()));
        }
    }
    
    @Override
    public void setCompletedObjective(UUID uniqueId, Quest quest, QuestObjective questObjective) {
        completedQuestObjectives.put(uniqueId, quest.getId(), questObjective.getId());
    }
    
    @Override
    public void setCompletedQuest(UUID uniqueId, Quest quest) {
        completedQuests.add(uniqueId, quest.getId());
    }
    
    @Override
    public boolean isQuestComplete(UUID uniqueId, String id) {
        if (completedQuests.containsKey(uniqueId)) {
            return completedQuests.get(uniqueId).contains(id);
        }
        return false;
    }
    
    @Override
    public boolean isQuestObjectiveComplete(UUID uniqueId, String questId, String objectiveId) {
        if (completedQuestObjectives.containsKey(uniqueId)) {
            String oid = completedQuestObjectives.get(uniqueId, questId);
            return oid != null;
        }
        return false;
    }
}
