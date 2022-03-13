package com.starmediadev.plugins.starquests.objects.actions;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.data.AmountQuestData;
import com.starmediadev.plugins.starquests.storage.StorageHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an action for breaking a certain amount of a type of block
 */
public class ItemConsumeAction extends EventAmountAction<Material, AmountQuestData, PlayerItemConsumeEvent> {
    
    /**
     * Construct a BlockBreakAction
     * @param material The material that needs to be broken
     * @param amount The amount of materials
     */
    public ItemConsumeAction(Material material, int amount) {
        super("itemconsume", material, amount);
        if (!material.isEdible()) {
            throw new RuntimeException("The material " + material.name() + " is not an edible item.");
        }
    }
    
    /**
     * Construct a BlockBreakAction
     * @param materials The materials that needs to be broken
     * @param amount The amount of materials
     */
    public ItemConsumeAction(List<Material> materials, int amount) {
        super("blockbreak", materials, amount);
        List<Material> inedibleMaterials = new ArrayList<>();
        for (Material material : materials) {
            if (!material.isEdible()) {
                inedibleMaterials.add(material);
            }
        }
        
        if (!inedibleMaterials.isEmpty()) {
            throw new RuntimeException("Found inedible materials " + inedibleMaterials);
        }
    }
    
    /**
     * Handles the event for the action. This is used internally
     * @param event The Bukkit Event
     * @param player
     * @param quest The quest that is being referred to
     * @param questObjective The objective
     * @param storageHandler The storage handler
     * @param questData The existing quest data. If it doesn't exist, it will be created
     * @return The current amount, used by the caller of this method
     */
    @SuppressWarnings("DuplicatedCode")
    @Override
    protected int handleEvent(PlayerItemConsumeEvent event, Player player, Quest quest, QuestObjective questObjective, StorageHandler storageHandler, AmountQuestData questData) {
        if (questData == null) {
            questData = new AmountQuestData(quest.getId(), questObjective.getId(), event.getPlayer().getUniqueId());
            storageHandler.addQuestData(event.getPlayer().getUniqueId(), questData);
        }
    
        ItemStack itemStack = event.getItem();
        Material type = itemStack.getType();
        if (this.types.contains(type)) {
            questData.increment(itemStack.getAmount());
        }
        return questData.getAmount();
    }
}
