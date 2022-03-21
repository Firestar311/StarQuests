package com.starmediadev.plugins.starquests.objects.actions;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LocationReachAction extends QuestAction<Location> {
    
    private Location location;
    private boolean exact;
    
    /**
     * {@inheritDoc}.
     */
    public LocationReachAction(Location location) {
        super("locationreach");
        this.location = new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
        this.exact = false;
    }
    
    public LocationReachAction(Location location, boolean exact) {
        super("locationreach");
        if (exact) {
            this.location = location;
        } else {
            this.location = new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
        }
        this.exact = exact;
    }
    
    /**
     * {@inheritDoc}.
     */
    @Override
    public void onAction(Location object, Player player, Quest quest, QuestObjective questObjective) {
        if (!exact) {
            object = new Location(object.getWorld(), object.getBlockX(), object.getBlockY(), object.getBlockZ());
        }
        if (location.equals(object)) {
            if (!questObjective.isComplete(player.getUniqueId())) {
                questObjective.complete(player.getUniqueId());
            }
        }
    }
}
