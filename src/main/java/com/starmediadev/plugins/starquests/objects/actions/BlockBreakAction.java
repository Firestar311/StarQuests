package com.starmediadev.plugins.starquests.objects.actions;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.data.BlockBreakQuestData;
import com.starmediadev.plugins.starquests.storage.StorageHandler;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Map;

public class BlockBreakAction extends EventAmountAction<Material, BlockBreakQuestData, BlockBreakEvent> {
    
    public BlockBreakAction(Map<Material, Integer> materials) {
        super("blockbreak", materials);
    }
    
    @Override
    protected Map<Material, Integer> handleEvent(BlockBreakEvent event, Quest quest, QuestObjective questObjective, StorageHandler storageHandler, BlockBreakQuestData questData) {
        if (questData == null) {
            questData = new BlockBreakQuestData(quest.getId(), questObjective.getId(), event.getPlayer().getUniqueId());
        }
        storageHandler.addQuestData(event.getPlayer().getUniqueId(), questData);
        
        Material type = event.getBlock().getType();
        if (this.requiredAmounts.containsKey(type)) {
            questData.addBrokenBlock(type);
        }
        return questData.getBlocksBroken();
    }
}
