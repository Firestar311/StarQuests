package com.starmediadev.plugins.starquests.objects;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class QuestLine extends QuestObject {
    protected Set<String> lineQuests = new HashSet<>();
    
    private QuestLine(String id) {
        super(id);
    }
    
    public List<Quest> getAvailableQuests(UUID player) {
        List<Quest> quests = getQuests();
        quests.removeIf(quest -> !quest.isAvailable(player));
        return quests;
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
    
    public void addQuest(Quest quest) {
        this.lineQuests.add(quest.getId());   
    }
}
