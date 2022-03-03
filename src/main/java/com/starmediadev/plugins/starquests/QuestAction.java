package com.starmediadev.plugins.starquests;

@FunctionalInterface
public interface QuestAction<T> {
    boolean onAction(T object, Quest quest, QuestObjective questObjective);
}
