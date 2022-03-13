package com.starmediadev.plugins.starquests.objects.rewards;

import com.starmediadev.plugins.starquests.objects.QuestObject;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * A reward for ItemStacks
 */
public class ItemReward extends QuestReward {
    /**
     * The ItemStack
     */
    protected final ItemStack itemStack;
    
    /**
     * Constructs a new ItemReward
     * @param id The ID for the reward
     * @param itemStack The itemstack for the reward
     */
    public ItemReward(String id, String title, QuestObject questObject, ItemStack itemStack) {
        super(id, title, questObject);
        this.itemStack = itemStack;
    }
    
    public ItemReward(String title, QuestObject questObject, ItemStack itemStack) {
        super(title, questObject);
        this.itemStack = itemStack;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void applyReward(Player player) throws Exception {
        if (player == null || !player.isOnline()) {
            throw new PlayerRewardException("Player is not online.");
        }
        player.getInventory().addItem(itemStack);
    }
}