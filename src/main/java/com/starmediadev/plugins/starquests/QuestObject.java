package com.starmediadev.plugins.starquests;

import java.util.HashSet;
import java.util.Set;

/**
 * The parent object for all base quest based objects like QuestLine, QuestPool and Quest
 */
public abstract class QuestObject {
    protected final QuestManager questManager;
    protected final String id;
    protected String displayName, description;
    protected boolean repeatable;
    protected Set<String> requiredQuestObjects = new HashSet<>(), optionalQuestRequirements = new HashSet<>();
    protected Set<QuestRequirement> requiredOtherRequirements = new HashSet<>();
    
    public QuestObject(QuestManager questManager, String id, String displayName, String description, boolean repeatable) {
        this.questManager = questManager;
        this.id = id;
        this.displayName = displayName;
        this.description = description;
        this.repeatable = repeatable;
    }
    
    public final QuestManager getQuestManager() {
        return questManager;
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
    
    public boolean isRepeatable() {
        return repeatable;
    }
    
    public void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
    }
}
