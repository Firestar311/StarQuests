package com.starmediadev.plugins.starquests;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.actions.QuestAction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.List;

public class ActionListener implements Listener {
    private static final StarQuests plugin = StarQuests.getInstance();
    
    private void handleActionEvent(Event e, Player player) {
        QuestManager questManager = plugin.getQuestManager();
        List<Quest> quests = questManager.getQuests();
        if (player == null) {
            return;
        }
        for (Quest quest : quests) {
            for (QuestObjective objective : quest.getObjectives()) {
                try {
                    QuestAction<Event> action = (QuestAction<Event>) objective.getQuestAction();
                    action.onAction(e, player, quest, objective);
                } catch (Exception ex) {}
            }
        }
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        handleActionEvent(e, e.getPlayer());
    }
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        handleActionEvent(e, e.getEntity().getKiller());
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        handleActionEvent(e, e.getPlayer());
    }
    
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        handleActionEvent(e, e.getPlayer());
    }
    
    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e) {
        if (e.getEntity() instanceof Player player) {
            handleActionEvent(e, player);
        }
    }
    
    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent e) {
        handleActionEvent(e, e.getPlayer());
    }
    
    @EventHandler
    public void onItemCraft(CraftItemEvent e) {
        if (e.getViewers().get(0) instanceof Player player) {
            handleActionEvent(e, player);
        }
    }
}