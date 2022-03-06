package com.starmediadev.plugins.starquests.objects.rewards;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemReward extends QuestReward {
    protected ItemStack itemStack;
    
    private ItemReward(Builder builder) {
        super(builder);
        this.itemStack = builder.itemStack;
    }
    
    @Override
    public void applyReward(Player player) throws Exception {
        if (player == null || !player.isOnline()) {
            throw new PlayerRewardException("Player is not online.");
        }
        player.getInventory().addItem(itemStack);
    }
    
    public static class Builder extends QuestReward.Builder<ItemReward, ItemReward.Builder> {
        protected ItemStack itemStack;
        
        public Builder itemStack(ItemStack itemStack) {
            this.itemStack = itemStack;
            return this;
        }
        
        @Override
        public ItemReward build() {
            return new ItemReward(this);
        }
    }
}
