package com.starmediadev.plugins.starquests;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.actions.BlockBreakAction;
import com.starmediadev.plugins.starquests.objects.actions.EntityKillAction;
import com.starmediadev.plugins.starquests.objects.actions.NPCClickAction;
import com.starmediadev.plugins.starquests.objects.actions.QuestAction;
import net.citizensnpcs.api.event.NPCClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;

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
    public void onNPCInteract(NPCClickEvent e) {
        QuestManager questManager = plugin.getQuestManager();
        List<Quest> quests = questManager.getQuests();
        for (Quest quest : quests) {
            for (QuestObjective objective : quest.getObjectives()) {
                QuestAction<?> action = objective.getQuestAction();
                if (action instanceof NPCClickAction npcClickAction) {
                    npcClickAction.onAction(e, quest, objective);
                }
            }
        }
    }
}
