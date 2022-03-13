package com.starmediadev.plugins.starquests.storage;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestLine;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.data.QuestData;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface StorageHandler {
    void addQuestData(UUID uniqueId, QuestData value);
    
    QuestData getQuestData(UUID uniqueId, Quest quest, QuestObjective questObjective);
    
    void removeQuestData(UUID uniqueId, Quest quest, QuestObjective questObjective);
    
    void setCompletedObjective(UUID uniqueId, Quest quest, QuestObjective questObjective);
    
    void setCompletedQuest(UUID uniqueId, Quest quest);
    
    boolean isQuestComplete(UUID uniqueId, String id);
    
    boolean isQuestObjectiveComplete(UUID uniqueId, String questId, String objectiveId);
    
    void setCompletedQuestLine(UUID uniqueId, QuestLine questLine);
    
    boolean isQuestLineComplete(UUID uuid, QuestLine questLine);
    
    JavaPlugin getPlugin();
    
    Map<String, String> getCachedQuestLineIds();
    
    Map<String, String> getCachedQuestIds();
    
    Map<String, String> getCachedObjectiveIds();
    
    Map<String, String> getCachedRewardIds();
    
    void setPlugin(JavaPlugin plugin);
    
    void setup();
    
    void saveData();
    
    void loadData();
    
    void reload();
    
    boolean isCachedId(String id);
}
