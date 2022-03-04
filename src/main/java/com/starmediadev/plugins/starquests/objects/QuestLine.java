package com.starmediadev.plugins.starquests.objects;

import com.starmediadev.plugins.starquests.QuestManager;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class QuestLine extends QuestObject {
    protected Set<String> quests = new HashSet<>();
    
    public QuestLine(QuestManager questManager, String id, String displayName, String description, boolean repeatable) {
        super(questManager, id, displayName, description, repeatable);
    }
    
    @Override
    public boolean isComplete(UUID player) {
        return false;
    }
    
    //TODO Get quests
}
