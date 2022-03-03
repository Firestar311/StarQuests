package com.starmediadev.plugins.starquests;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface QuestRequirement {
    boolean checkSatisfies(Player player);
}
