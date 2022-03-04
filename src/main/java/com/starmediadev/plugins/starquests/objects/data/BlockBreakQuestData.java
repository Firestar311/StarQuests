package com.starmediadev.plugins.starquests.objects.data;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BlockBreakQuestData extends QuestData {
    private Map<Material, Integer> blocksBroken = new HashMap<>();
    
    public BlockBreakQuestData(String questId, String questObjectiveId, UUID uniqueId) {
        super(questId, questObjectiveId, uniqueId);
    }
    
    public void addBrokenBlock(Material material) {
        blocksBroken.put(material, blocksBroken.getOrDefault(material, 0) + 1);
    }
    
    public Map<Material, Integer> getBlocksBroken() {
        return blocksBroken;
    }
}
