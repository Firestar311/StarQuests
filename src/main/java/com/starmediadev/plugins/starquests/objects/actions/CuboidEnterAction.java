package com.starmediadev.plugins.starquests.objects.actions;

import com.starmediadev.plugins.starmcutils.region.Cuboid;
import com.starmediadev.plugins.starquests.objects.*;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CuboidEnterAction extends QuestAction<Location> {
    
    private Cuboid cuboid;
    
    /**
     * {@inheritDoc}.
     */
    public CuboidEnterAction(Cuboid cuboid) {
        super("cuboidenter");
        this.cuboid = cuboid;
    }
    
    /**
     * {@inheritDoc}.
     */
    @Override
    public void onAction(Location object, Player player, Quest quest, QuestObjective questObjective) {
        if (cuboid.contains(object)) {
            if (!questObjective.isComplete(player.getUniqueId())) {
                questObjective.complete(player.getUniqueId());
            }
        }
    }
}
