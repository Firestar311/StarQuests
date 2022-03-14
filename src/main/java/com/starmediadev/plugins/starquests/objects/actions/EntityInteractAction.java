package com.starmediadev.plugins.starquests.objects.actions;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class EntityInteractAction extends QuestAction<PlayerInteractAtEntityEvent> {
    
    private EntityType entityType;
    
    /**
     * {@inheritDoc}.
     */
    public EntityInteractAction(EntityType entityType) {
        super("entityinteract");
        this.entityType = entityType;
    }
    
    /**
     * {@inheritDoc}.
     */
    @Override
    public void onAction(PlayerInteractAtEntityEvent object, Player player, Quest quest, QuestObjective questObjective) {
        if (object.getRightClicked().getType() == entityType) {
            if (!questObjective.isComplete(player.getUniqueId())) {
                questObjective.complete(player.getUniqueId());
            }
        }
    }
}
