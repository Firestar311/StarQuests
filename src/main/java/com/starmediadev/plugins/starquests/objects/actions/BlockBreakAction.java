package com.starmediadev.plugins.starquests.objects.actions;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.data.AmountQuestData;
import com.starmediadev.plugins.starquests.storage.StorageHandler;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakAction extends EventAmountAction<Material, AmountQuestData, BlockBreakEvent> {
    
    public BlockBreakAction(Material material, int amount) {
        super("blockbreak", material, amount);
    }
    
    @Override
    protected int handleEvent(BlockBreakEvent event, Quest quest, QuestObjective questObjective, StorageHandler storageHandler, AmountQuestData questData) {
        if (questData == null) {
            questData = new AmountQuestData(quest.getId(), questObjective.getId(), event.getPlayer().getUniqueId());
            storageHandler.addQuestData(event.getPlayer().getUniqueId(), questData);
        }
        
        Material type = event.getBlock().getType();
        if (this.type == type) {
            questData.increment();
        }
        return questData.getAmount();
    }
}
