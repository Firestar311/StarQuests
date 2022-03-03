package com.starmediadev.plugins.starquests;

import java.util.HashSet;
import java.util.Set;

public class QuestLine extends QuestObject {
    protected Set<String> quests = new HashSet<>();
    
    public QuestLine(QuestManager questManager, String id, String displayName, String description, boolean repeatable) {
        super(questManager, id, displayName, description, repeatable);
    }
    
    //TODO Get quests
}
