package com.starmediadev.plugins.starquests.objects;

import com.starmediadev.plugins.starmcutils.util.MCUtils;
import com.starmediadev.plugins.starquests.QuestManager;
import com.starmediadev.plugins.starquests.objects.rewards.QuestReward;
import com.starmediadev.plugins.starquests.storage.StorageHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Quest extends QuestObject {
    protected Set<QuestObjective> objectives = new HashSet<>();
    protected Set<QuestReward> rewards = new HashSet<>();
    
    public Quest(String id) {
        super(id);
    }
    
    public Quest(String id, String title) {
        super(id, title);
    }
    
    public void addObjective(QuestObjective objective) {
        objective.setQuest(this);
        this.objectives.add(objective);
    }
    
    @Override
    public boolean meetsPrequisites(UUID player) {
        for (QuestObject prerequisite : this.prerequisiteObjects) {
            if (prerequisite instanceof QuestLine line) {
                if (!line.isComplete(player)) {
                    if (!line.isAvailable(player)) {
                        return false;
                    }
                }
            } else {
                if (!prerequisite.isComplete(player)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public boolean isComplete(UUID player) {
        if (questManager.isQuestComplete(player, this)) {
            return true;
        }
    
        boolean allObjectivesComplete = true;
        for (QuestObjective objective : objectives) {
            if (!objective.isComplete(player)) {
                allObjectivesComplete = false;
                break;
            }
        }
        
        return allObjectivesComplete;
    }
    
    public Set<QuestObjective> getObjectives() {
        return new HashSet<>(objectives);
    }
    
    public Set<QuestReward> getRewards() {
        return new HashSet<>(rewards);
    }
    
    @Override
    public void complete(UUID uniqueId) {
        QuestManager questManager = getQuestManager();
        StorageHandler storageHandler = questManager.getStorageHandler();
        if (!storageHandler.isQuestComplete(uniqueId, getId())) {
            storageHandler.setCompletedQuest(uniqueId, this);
            Player player = Bukkit.getPlayer(uniqueId);
            if (player != null) {
                player.sendMessage(MCUtils.color("Completed Quest: " + getTitle()));
                getRewards().forEach(reward -> {
                    try {
                        reward.applyReward(player);
                    } catch (Exception e) {
                        player.sendMessage(MCUtils.color("&cError applying reward " + e.getMessage()));
                    }
                });
            }
        }
    
        for (QuestLine questLine : questManager.getQuestLineRegistry().getAllRegistered()) {
            for (Quest quest : questLine.getQuests()) {
                if (quest.getId().equals(this.id)) {
                    if (questLine.isComplete(uniqueId)) {
                        questLine.complete(uniqueId);
                    }
                }
            }
        }
    }
}
