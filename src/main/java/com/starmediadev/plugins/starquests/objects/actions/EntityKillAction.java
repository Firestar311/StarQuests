package com.starmediadev.plugins.starquests.objects.actions;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.data.AmountQuestData;
import com.starmediadev.plugins.starquests.storage.StorageHandler;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;

/**
 * Represents an action to kill a certaim amount of entities
 */
public class EntityKillAction extends EventAmountAction<EntityType, EntityDeathEvent> {
    
    /**
     * Construct an EntityKillAction
     * @param type The entity type
     * @param amount The amount to kill
     */
    public EntityKillAction(EntityType type, int amount) {
        super("entitykill", type, amount);
    }
    
    /**
     * Construct an EntityKillAction
     * @param types The entity types
     * @param amount The amount to kill
     */
    public EntityKillAction(List<EntityType> types, int amount) {
        super("entitykill", types, amount);
    }
    
    /**
     * Handles the event for the action. This is used internally
     * @param event The Bukkit Event
     * @param player The player
     * @param quest The quest that is being referred to
     * @param questObjective The objective
     * @param storageHandler The storage handler
     * @param questData The existing quest data. If it doesn't exist, it will be created
     */
    @Override
    protected void handleEvent(EntityDeathEvent event, Player player, Quest quest, QuestObjective questObjective, StorageHandler storageHandler, AmountQuestData questData) {
        EntityType type = event.getEntityType();
        if (this.types.contains(type)) {
            questData.increment();
        }
    }
}
