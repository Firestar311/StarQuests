package com.starmediadev.plugins.starquests.objects.actions;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.data.AmountQuestData;
import com.starmediadev.plugins.starquests.storage.StorageHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Represents an action for breaking a certain amount of a type of block
 */
public class ItemPickupAction extends EventAmountAction<Material, AmountQuestData, EntityPickupItemEvent> {
    
    /**
     * Construct a BlockBreakAction
     * @param material The material that needs to be broken
     * @param amount The amount of materials
     */
    public ItemPickupAction(Material material, int amount) {
        super("blockbreak", material, amount);
    }
    
    /**
     * Construct a BlockBreakAction
     * @param materials The materials that needs to be broken
     * @param amount The amount of materials
     */
    public ItemPickupAction(List<Material> materials, int amount) {
        super("blockbreak", materials, amount);
    }
    
    /**
     * Handles the event for the action. This is used internally
     * @param event The Bukkit Event
     * @param quest The quest that is being referred to
     * @param questObjective The objective
     * @param storageHandler The storage handler
     * @param questData The existing quest data. If it doesn't exist, it will be created
     * @return The current amount, used by the caller of this method
     */
    @SuppressWarnings("DuplicatedCode")
    @Override
    protected int handleEvent(EntityPickupItemEvent event, Quest quest, QuestObjective questObjective, StorageHandler storageHandler, AmountQuestData questData) {
        Player player = ((Player) event.getEntity());
        if (questData == null) {
            questData = new AmountQuestData(quest.getId(), questObjective.getId(), player.getUniqueId());
            storageHandler.addQuestData(player.getUniqueId(), questData);
        }
    
        ItemStack itemStack = event.getItem().getItemStack();
        Material type = itemStack.getType();
        if (this.types.contains(type)) {
            questData.increment(itemStack.getAmount());
        }
        return questData.getAmount();
    }
}