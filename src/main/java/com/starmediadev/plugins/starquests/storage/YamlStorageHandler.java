package com.starmediadev.plugins.starquests.storage;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.data.QuestData;
import com.starmediadev.utils.collection.ListMap;
import com.starmediadev.utils.collection.MultiMap;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class YamlStorageHandler implements StorageHandler {
    
    private ListMap<UUID, QuestData> questDataMap = new ListMap<>();
    private ListMap<UUID, String> completedQuests = new ListMap<>();
    private MultiMap<UUID, String, List<String>> completedQuestObjectives = new MultiMap<>();
    
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
        List<String> completedObjectives = completedQuestObjectives.get(uniqueId, quest.getId());
        if (completedObjectives == null) {
            completedObjectives = new ArrayList<>();
            completedQuestObjectives.put(uniqueId, quest.getId(), completedObjectives);
        }
        completedObjectives.add(questObjective.getId());
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
            List<String> completedObjectives = completedQuestObjectives.get(uniqueId, questId);
            if (completedObjectives != null) {
                return completedObjectives.contains(objectiveId);
            }
        }
        return false;
    }
}
