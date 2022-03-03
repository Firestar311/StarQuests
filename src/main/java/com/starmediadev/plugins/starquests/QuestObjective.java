package com.starmediadev.plugins.starquests;

import java.util.HashSet;
import java.util.Set;

public class QuestObjective {
    protected final String id;
    protected String displayName, description;
    protected Set<QuestAction> actions = new HashSet<>();
    
    public QuestObjective(String id, String displayName, String description) {
        this.id = id;
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getId() {
        return id;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Set<QuestAction> getActions() {
        return actions;
    }
    
    public void setActions(Set<QuestAction> actions) {
        this.actions = actions;
    }
}
