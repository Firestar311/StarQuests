package com.starmediadev.plugins.starquests.objects.interfaces;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface QuestRequirement {
    boolean checkSatisfies(Player player);
}
