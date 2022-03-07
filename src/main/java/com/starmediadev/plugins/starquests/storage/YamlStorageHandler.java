package com.starmediadev.plugins.starquests.storage;

import com.starmediadev.plugins.starmcutils.util.Config;
import com.starmediadev.plugins.starquests.StarQuests;
import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.data.QuestData;
import com.starmediadev.utils.collection.ListMap;
import com.starmediadev.utils.collection.MultiMap;
import com.starmediadev.utils.helper.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.*;

public class YamlStorageHandler implements StorageHandler {
    
    private ListMap<UUID, QuestData> questDataMap = new ListMap<>();
    private ListMap<UUID, String> completedQuests = new ListMap<>();
    private MultiMap<UUID, String, List<String>> completedQuestObjectives = new MultiMap<>();
    
    private final StarQuests plugin = StarQuests.getInstance();
    private Config completedQuestsConfig, completedQuestObjectivesConfig, questDataConfig;
    
    
    @Override
    public void addQuestData(UUID uniqueId, QuestData value) {
        List<QuestData> questDataList = questDataMap.get(uniqueId);
        if (questDataList == null || questDataList.isEmpty()) {
            questDataMap.add(uniqueId, value);
        } else {
            boolean hasMatch = false;
            for (QuestData questData : questDataList) {
                if (questData.equals(value)) {
                    hasMatch = true;
                    break;
                }
            }
            
            if (hasMatch) {
                int index = questDataList.indexOf(value);
                questDataList.set(index, value);
            }
        }
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
            List<QuestData> questDataList = questDataMap.get(uniqueId);
            QuestData toRemove = null;
            for (QuestData questData : questDataList) {
                if (questData.getQuestId().equals(quest.getId()) && questData.getQuestObjectiveId().equals(questObjective.getId())) {
                    toRemove = questData;
                    break;
                }
            }
            
            if (toRemove != null) {
                questDataList.remove(toRemove);
            }
            questDataMap.put(uniqueId, questDataList);
        }
    }
    
    @Override
    public void removeCompletedObjectives(UUID uniqueId, Quest quest) {
        Map<String, List<String>> allCompletedObjectives = this.completedQuestObjectives.get(uniqueId);
        if (allCompletedObjectives != null && !allCompletedObjectives.isEmpty()) {
            allCompletedObjectives.remove(quest.getId());
        }
    }
    
    @Override
    public void removeCompletedObjective(UUID uuid, Quest quest, QuestObjective questObjective) {
        Map<String, List<String>> allCompletedObjectives = this.completedQuestObjectives.get(uuid);
        if (allCompletedObjectives != null && !allCompletedObjectives.isEmpty()) {
            List<String> completedObjectives = allCompletedObjectives.get(quest.getId());
            completedObjectives.remove(questObjective.getId());
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
        removeQuestData(uniqueId, quest, questObjective);
    }
    
    @Override
    public void setCompletedQuest(UUID uniqueId, Quest quest) {
        completedQuests.add(uniqueId, quest.getId());
        for (QuestObjective objective : quest.getObjectives()) {
            removeQuestData(uniqueId, quest, objective);
        }
        removeCompletedObjectives(uniqueId, quest);
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
    
    @Override
    public void setup() {
        completedQuestsConfig = new Config(plugin, "completedquests.yml");
        completedQuestObjectivesConfig = new Config(plugin, "completedobjectives.yml");
        questDataConfig = new Config(plugin, "questdata.yml");
        
        completedQuestsConfig.setup();
        completedQuestObjectivesConfig.setup();
        questDataConfig.setup();
    }
    
    @Override
    public void saveData() {
        completedQuestsConfig.set("players", null);
        completedQuestsConfig.save();
        
        completedQuestObjectivesConfig.set("players", null);
        completedQuestObjectivesConfig.save();
        
        questDataConfig.set("players", null);
        questDataConfig.save();
        
        completedQuests.forEach((player, questsCompleted) -> completedQuestsConfig.set("players." + player.toString(), questsCompleted));
    
        for (UUID player : completedQuestObjectives.keySet()) {
            Map<String, List<String>> questsAndObjectives = completedQuestObjectives.get(player);
            Set<String> questIds = questsAndObjectives.keySet();
            for (String questId : questIds) {
                List<String> objectives = questsAndObjectives.get(questId);
                completedQuestObjectivesConfig.set("players." + player.toString() + ".quests." + questId + ".objectives", objectives);
            }
        }
        
        questDataMap.forEach((player, questDataList) -> {
            for (QuestData questData : questDataList) {
                Map<String, Object> data = new HashMap<>();
                Set<Field> fields = ReflectionHelper.getClassFields(questData.getClass());
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (ReflectionHelper.isFinal(field)) {
                        ReflectionHelper.makeNonFinal(field);
                    }
    
                    try {
                        Object value;
                        if (UUID.class.isAssignableFrom(field.getType())) {
                            value = field.get(questData).toString();
                        } else {
                            value = field.get(questData);
                        }
                        data.put(field.getName(), value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                
                data.forEach((key, value) -> questDataConfig.set("players." + player.toString() + ".quests." + questData.getQuestId() + ".objectives." + questData.getQuestObjectiveId() + ".data." + key, value));
            }
        });
        
        this.completedQuestsConfig.save();
        this.completedQuestObjectivesConfig.save();
        this.questDataConfig.save();
    }
    
    @Override
    public void loadData() {
        
    }
}
