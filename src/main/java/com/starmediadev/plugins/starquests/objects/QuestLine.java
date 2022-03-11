package com.starmediadev.plugins.starquests.objects;

import com.starmediadev.plugins.starmcutils.util.MCUtils;
import com.starmediadev.plugins.starquests.QuestManager;
import com.starmediadev.plugins.starquests.storage.StorageHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class QuestLine extends QuestObject {
    protected Set<Quest> quests = new HashSet<>();
    
    private QuestLine(String id) {
        super(id);
    }
    
    public List<Quest> getAvailableQuests(UUID player) {
        List<Quest> quests = getQuests();
        quests.removeIf(quest -> !quest.isAvailable(player));
        return quests;
    }
    
    @Override
    public boolean isComplete(UUID player) {
        if (questManager.isQuestLineComplete(player, this)) {
            return true;
        }
        for (Quest quest : getQuests()) {
            if (!quest.isComplete(player)) {
                return false;
            }
            
            for (QuestObject sideQuestObject : sideQuestObjects) {
                if (!sideQuestObject.isComplete(player)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean isRequiredQuestsComplete(UUID player) {
        for (Quest quest : getQuests()) {
            if (!quest.isComplete(player)) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public void complete(UUID uniqueId) {
        QuestManager questManager = getQuestManager();
        StorageHandler storageHandler = questManager.getStorageHandler();
        if (!storageHandler.isQuestLineComplete(uniqueId, this)) {
            storageHandler.setCompletedQuestLine(uniqueId, this);
            Player player = Bukkit.getPlayer(uniqueId);
            if (player != null) {
                player.sendMessage(MCUtils.color("Completed Quest Line: " + getTitle()));
                getRewards().forEach(reward -> {
                    try {
                        reward.applyReward(player);
                    } catch (Exception e) {
                        player.sendMessage(MCUtils.color("&cError applying reward " + e.getMessage()));
                    }
                });
            }
        }
    }
    
    public List<Quest> getQuests() {
        return new ArrayList<>(quests);
    }
    
    public void addQuest(Quest quest) {
        this.quests.add(quest);
    }
}
