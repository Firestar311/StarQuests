package com.starmediadev.plugins.starquests.objects;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class QuestLine extends QuestObject {
    protected Set<String> lineQuests = new HashSet<>();
    
//    public QuestLine(QuestManager questManager, String id, String displayName, String description, boolean repeatable) {
//        super(questManager, id, displayName, description, repeatable);
//    }
    
    
    public QuestLine(Builder<?, ?> builder, Set<String> lineQuests) {
        super(builder);
        this.lineQuests = lineQuests;
    }
    
    @Override
    public boolean isComplete(UUID player) {
        List<Quest> quests = getQuests();
        for (Quest quest : quests) {
            if (!quest.isOptional()) {
                if (!quest.isComplete(player)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public List<Quest> getQuests() {
        List<Quest> quests = questManager.getQuests();
        quests.removeIf(quest -> !lineQuests.contains(quest.getId()));
        return quests;
    }
}
