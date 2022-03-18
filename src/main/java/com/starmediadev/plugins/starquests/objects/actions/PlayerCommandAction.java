package com.starmediadev.plugins.starquests.objects.actions;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class PlayerCommandAction extends QuestAction<PlayerCommandPreprocessEvent> {
    
    private String command;
    
    /**
     * {@inheritDoc}.
     */
    public PlayerCommandAction(String command) {
        super("entityinteract");
        this.command = command;
    }
    
    /**
     * {@inheritDoc}.
     */
    @Override
    public void onAction(PlayerCommandPreprocessEvent object, Player player, Quest quest, QuestObjective questObjective) {
        if (object.getMessage().startsWith(this.command)) {
            if (!questObjective.isComplete(player.getUniqueId())) {
                questObjective.complete(player.getUniqueId());
            }
        }
    }
}
