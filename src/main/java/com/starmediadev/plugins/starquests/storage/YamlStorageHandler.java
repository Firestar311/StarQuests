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
                        data.put(field.getName(), field.get(questData));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                
                data.forEach((key, value) -> questDataConfig.set("players." + player.toString() + ".quests." + questData.getQuestId() + ".objectives." + questData.getQuestObjectiveId() + ".data." + key, data));
            }
        });
        
    }
    
    @Override
    public void loadData() {
        
    }
}
