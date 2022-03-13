package com.starmediadev.plugins.starquests.objects.actions;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.data.AmountQuestData;
import com.starmediadev.plugins.starquests.storage.StorageHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

/**
 * Represents an action for breaking a certain amount of a type of block
 */
public class BlockBreakAction extends EventAmountAction<Material, AmountQuestData, BlockBreakEvent> {
    
    /**
     * Construct a BlockBreakAction
     *
     * @param material The material that needs to be broken
     * @param amount   The amount of materials
     */
    public BlockBreakAction(Material material, int amount) {
        super("blockbreak", material, amount);
    }
    
    /**
     * Construct a BlockBreakAction
     *
     * @param materials The materials that needs to be broken
     * @param amount    The amount of materials
     */
    public BlockBreakAction(List<Material> materials, int amount) {
        super("blockbreak", materials, amount);
    }
    
    /**
     * Handles the event for the action. This is used internally
     *
     * @param event          The Bukkit Event
     * @param player         The player
     * @param quest          The quest that is being referred to
     * @param questObjective The objective
     * @param storageHandler The storage handler
     * @param questData      The existing quest data. If it doesn't exist, it will be created
     * @return The current amount, used by the caller of this method
     */
    @SuppressWarnings("DuplicatedCode")
    @Override
    protected int handleEvent(BlockBreakEvent event, Player player, Quest quest, QuestObjective questObjective, StorageHandler storageHandler, AmountQuestData questData) {
        if (questData == null) {
            questData = new AmountQuestData(quest.getId(), questObjective.getId(), event.getPlayer().getUniqueId());
            storageHandler.addQuestData(event.getPlayer().getUniqueId(), questData);
        }
        
        Material type = event.getBlock().getType();
        if (this.types.contains(type)) {
            questData.increment();
        }
        return questData.getAmount();
    }
}
