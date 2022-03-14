package com.starmediadev.plugins.starquests.objects.actions;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.data.AmountQuestData;
import com.starmediadev.plugins.starquests.storage.StorageHandler;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerBucketEntityEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Represents an action for breaking a certain amount of a type of block
 */
public class BucketEntityCaptureAction extends EventAmountAction<EntityType, PlayerBucketEntityEvent> {
    
    /**
     * Construct a BucketFillAction
     *
     * @param entityType The material that needs to be broken
     * @param amount   The amount of materials
     */
    public BucketEntityCaptureAction(EntityType entityType, int amount) {
        super("bucketentitycapture", entityType, amount);
    }
    
    /**
     * Construct a BlockBreakAction
     *
     * @param entityTypes The materials that needs to be broken
     * @param amount    The amount of materials
     */
    public BucketEntityCaptureAction(List<EntityType> entityTypes, int amount) {
        super("bucketentitycapture", entityTypes, amount);
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
    protected void handleEvent(PlayerBucketEntityEvent event, Player player, Quest quest, QuestObjective questObjective, StorageHandler storageHandler, AmountQuestData questData) {
        if (this.types.contains(event.getEntity().getType())) {
            questData.increment();
        }
    }
}
