package com.starmediadev.plugins.starquests;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.actions.QuestAction;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

import java.util.List;

public class ActionListener implements Listener {
    private static final StarQuests plugin = StarQuests.getInstance();
    
    public ActionListener() {
        Bukkit.getServer().getScheduler().runTaskTimer(plugin, () -> {
            for (Quest quest : plugin.getQuestManager().getQuests()) {
                if (quest.isActive()) {
                    for (QuestObjective objective : quest.getObjectives()) {
                        if (objective.isActive()) {
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                if (objective.isAvailable(player.getUniqueId())) {
                                    try {
                                        QuestAction<Location> questAction = (QuestAction<Location>) objective.getQuestAction();
                                        questAction.onAction(player.getLocation(), player, quest, objective);
                                    } catch (Exception e) {}
                                }
                            }
                        }
                    }
                }
            }
        }, 1L, 1L);
    }
    
    private void handleActionEvent(Event e, Player player) {
        QuestManager questManager = plugin.getQuestManager();
        List<Quest> quests = questManager.getQuests();
        if (player == null) {
            return;
        }
        for (Quest quest : quests) {
            if (quest.isAvailable(player.getUniqueId())) {
                for (QuestObjective objective : quest.getObjectives()) {
                    if (objective.isAvailable(player.getUniqueId())) {
                        try {
                            QuestAction<Event> action = (QuestAction<Event>) objective.getQuestAction();
                            action.onAction(e, player, quest, objective);
                        } catch (Exception ex) {
                        }
                    }
                }
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
    
    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent e) {
        handleActionEvent(e, e.getPlayer());
    }
    
    @EventHandler
    public void onBedLeave(PlayerBedLeaveEvent e) {
        handleActionEvent(e, e.getPlayer());
    }
    
    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent e) {
        handleActionEvent(e, e.getPlayer());
    }
    
    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent e) {
        handleActionEvent(e, e.getPlayer());
    }
    
    @EventHandler
    public void onBucketEntityCapture(PlayerBucketEntityEvent e) {
        handleActionEvent(e, e.getPlayer());
    }
    
    @EventHandler
    public void onEntityShear(PlayerShearEntityEvent e) {
        handleActionEvent(e, e.getPlayer());
    }
    
    @EventHandler
    public void onEntityTame(EntityTameEvent e) {
        if (e.getOwner() instanceof Player player) {
            handleActionEvent(e, player);
        }
    }
    
    @EventHandler
    public void onEntityBreed(EntityBreedEvent e) {
        if (e.getBreeder() instanceof Player player) {
            handleActionEvent(e, player);
        }
    }
    
    @EventHandler
    public void onEntityInteract(PlayerInteractAtEntityEvent e) {
        handleActionEvent(e, e.getPlayer());
    }
    
    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        handleActionEvent(e, e.getPlayer());
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        handleActionEvent(e, (Player) e.getWhoClicked());
    }
    
    @EventHandler
    public void onItemEnchant(EnchantItemEvent e) {
        handleActionEvent(e, e.getEnchanter());
    }
}