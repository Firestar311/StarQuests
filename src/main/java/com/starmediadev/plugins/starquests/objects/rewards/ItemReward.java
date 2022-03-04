package com.starmediadev.plugins.starquests.objects.rewards;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemReward extends QuestReward {
    protected ItemStack itemStack;
    
    public ItemReward(String id, String displayName, ItemStack itemStack) {
        super(id, displayName);
        this.itemStack = itemStack;
    }
    
    @Override
    void applyReward(Player player) throws Exception {
        if (player == null || !player.isOnline()) {
            throw new PlayerRewardException("Player is not online.");
        }
        player.getInventory().addItem(itemStack);
    }
}
