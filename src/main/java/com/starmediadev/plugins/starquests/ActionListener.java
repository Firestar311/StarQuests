package com.starmediadev.plugins.starquests;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.actions.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.List;

public class ActionListener implements Listener {
    private static final StarQuests plugin = StarQuests.getInstance();
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        QuestManager questManager = plugin.getQuestManager();
        List<Quest> quests = questManager.getQuests();
        for (Quest quest : quests) {
            for (QuestObjective objective : quest.getObjectives()) {
                QuestAction<?> action = objective.getQuestAction();
                if (action instanceof BlockBreakAction blockBreakAction) {
                    blockBreakAction.onAction(e, quest, objective);
                }
            }
        }
    }
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().getKiller() == null) {
            return;
        }
        QuestManager questManager = plugin.getQuestManager();
        List<Quest> quests = questManager.getQuests();
        for (Quest quest : quests) {
            for (QuestObjective objective : quest.getObjectives()) {
                QuestAction<?> action = objective.getQuestAction();
                if (action instanceof EntityKillAction mobKillAction) {
                    mobKillAction.onAction(e, quest, objective);
                }
            }
        }
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.getPlayer() == null) {
            return;
        }
        QuestManager questManager = plugin.getQuestManager();
        List<Quest> quests = questManager.getQuests();
        for (Quest quest : quests) {
            for (QuestObjective objective : quest.getObjectives()) {
                QuestAction<?> action = objective.getQuestAction();
                if (action instanceof BlockPlaceAction blockPlaceAction) {
                    blockPlaceAction.onAction(e, quest, objective);
                }
            }
        }
    }
    
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        if (e.getPlayer() == null) {
            return;
        }
        QuestManager questManager = plugin.getQuestManager();
        List<Quest> quests = questManager.getQuests();
        for (Quest quest : quests) {
            for (QuestObjective objective : quest.getObjectives()) {
                QuestAction<?> action = objective.getQuestAction();
                if (action instanceof ItemDropAction itemDropAction) {
                    itemDropAction.onAction(e, quest, objective);
                }
            }
        }
    }
    
    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e) {
        if (e.getEntity() == null || !(e.getEntity() instanceof Player)) {
            return;
        }
        QuestManager questManager = plugin.getQuestManager();
        List<Quest> quests = questManager.getQuests();
        for (Quest quest : quests) {
            for (QuestObjective objective : quest.getObjectives()) {
                QuestAction<?> action = objective.getQuestAction();
                if (action instanceof ItemPickupAction itemPickupAction) {
                    itemPickupAction.onAction(e, quest, objective);
                }
            }
        }
    }
}