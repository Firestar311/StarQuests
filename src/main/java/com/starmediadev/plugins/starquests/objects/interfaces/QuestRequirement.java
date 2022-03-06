package com.starmediadev.plugins.starquests.objects.interfaces;

import java.util.UUID;

@FunctionalInterface
public interface QuestRequirement {
    boolean checkSatisfies(UUID player);
}
