package com.starmediadev.plugins.starquests.objects.actions;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.data.AmountQuestData;
import com.starmediadev.plugins.starquests.storage.StorageHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerBucketFillEvent;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Represents an action for breaking a certain amount of a type of block
 */
public class BucketFillAction extends EventAmountAction<Material, PlayerBucketFillEvent> {
    
    private static final Set<Material> ACCEPTED_MATERIALS = EnumSet.of(Material.WATER, Material.WATER_BUCKET, Material.LAVA, Material.LAVA_BUCKET);
    
    /**
     * Construct a BucketFillAction
     *
     * @param material The material that needs to be broken
     * @param amount   The amount of materials
     */
    public BucketFillAction(Material material, int amount) {
        super("bucketfill", material, amount);
        if (!ACCEPTED_MATERIALS.contains(material)) {
            throw new RuntimeException("Invalid material for bucketfill action " + material);
        }
    }
    
    /**
     * Construct a BlockBreakAction
     *
     * @param materials The materials that needs to be broken
     * @param amount    The amount of materials
     */
    public BucketFillAction(List<Material> materials, int amount) {
        super("bucketfill", materials, amount);
        for (Material material : materials) {
            if (!ACCEPTED_MATERIALS.contains(material)) {
                throw new RuntimeException("Invalid material for bucketfill action " + material);
            }
        }
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
     */
    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void handleEvent(PlayerBucketFillEvent event, Player player, Quest quest, QuestObjective questObjective, StorageHandler storageHandler, AmountQuestData questData) {
        if (this.types.contains(event.getBucket()) || this.types.contains(event.getBlock().getType())) {
            questData.increment();
        }
    }
}
