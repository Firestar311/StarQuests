package com.starmediadev.plugins.starquests;

import java.util.HashSet;
import java.util.Set;

public class Quest extends QuestObject {
    protected Set<QuestObjective> objectives = new HashSet<>();
    protected Set<QuestReward> rewards = new HashSet<>();
    
    public Quest(QuestManager questManager, String id, String displayName, String description, boolean repeatable) {
        super(questManager, id, displayName, description, repeatable);
    }
}
