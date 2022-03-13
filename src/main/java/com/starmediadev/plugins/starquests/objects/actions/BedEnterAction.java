package com.starmediadev.plugins.starquests.objects.actions;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class BedEnterAction extends QuestAction<PlayerBedEnterEvent> {
    /**
     * {@inheritDoc}.
     */
    public BedEnterAction() {
        super("bedenter");
    }
    
    @Override
    public void onAction(PlayerBedEnterEvent object, Player player, Quest quest, QuestObjective questObjective) {
        
    }
}
