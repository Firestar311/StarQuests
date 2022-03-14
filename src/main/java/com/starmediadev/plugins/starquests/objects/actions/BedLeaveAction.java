package com.starmediadev.plugins.starquests.objects.actions;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class BedLeaveAction extends QuestAction<PlayerBedEnterEvent> {
    /**
     * {@inheritDoc}.
     */
    public BedLeaveAction() {
        super("bedleave");
    }
    
    /**
     * {@inheritDoc}.
     */
    @Override
    public void onAction(PlayerBedEnterEvent object, Player player, Quest quest, QuestObjective questObjective) {
        if (!questObjective.isComplete(player.getUniqueId())) {
            questObjective.complete(player.getUniqueId());
        }
    }
}
