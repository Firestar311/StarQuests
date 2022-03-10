package com.starmediadev.plugins.starquests.objects;

import com.starmediadev.plugins.starmcutils.util.MCUtils;
import com.starmediadev.plugins.starquests.QuestManager;
import com.starmediadev.plugins.starquests.StarQuests;
import com.starmediadev.plugins.starquests.objects.actions.QuestAction;
import com.starmediadev.plugins.starquests.storage.StorageHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class QuestObjective extends QuestObject {
    protected String questId;
    protected final QuestAction<?> questAction;
    
    private QuestObjective(String id, String questId, QuestAction<?> questAction) {
        super(id);
        this.questId = questId;
        this.questAction = questAction;
    }
    
    public String getId() {
        return id;
    }
    
    public String getQuestId() {
        return questId;
    }
    
    public void setQuestId(String questId) {
        this.questId = questId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getName() {
        return name;
    }
    
    public QuestObjective setName(String name) {
        this.name = name;
        return this;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public QuestAction<?> getQuestAction() {
        return questAction;
    }
    
    public void complete(UUID uniqueId) {
        QuestManager questManager = StarQuests.getInstance().getQuestManager();
        StorageHandler storageHandler = questManager.getStorageHandler();
        Quest quest = questManager.getQuest(questId);
        storageHandler.setCompletedObjective(uniqueId, quest, this);
        Player player = Bukkit.getPlayer(uniqueId);
        if (player != null) {
            player.sendMessage(MCUtils.color("Completed Objective: " + getTitle()));
        }
        if (quest.isComplete(uniqueId)) {
            storageHandler.setCompletedQuest(uniqueId, quest);
            if (player != null) {
                player.sendMessage(MCUtils.color("Completed Quest: " + quest.getTitle()));
                quest.getRewards().forEach(reward -> {
                    try {
                        reward.applyReward(player);
                    } catch (Exception e) {
                        player.sendMessage(MCUtils.color("&cError applying reward " + e.getMessage()));
                    }
                });
            }
        }
    }
    
    public boolean isComplete(UUID player) {
        QuestManager questManager = StarQuests.getInstance().getQuestManager();
        Quest quest = questManager.getQuest(questId);
        if (questManager.isQuestComplete(player, quest)) {
            return true;
        }
        return questManager.isQuestObjectiveComplete(player, quest, this);
    }
}