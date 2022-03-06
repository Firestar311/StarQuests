package com.starmediadev.plugins.starquests.objects;

import com.starmediadev.plugins.starquests.QuestManager;
import com.starmediadev.plugins.starquests.objects.interfaces.QuestRequirement;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * The parent object for all base quest based objects like QuestLine, QuestPool and Quest
 */
public abstract class QuestObject {
    protected final QuestManager questManager;
    protected final String id;
    protected String displayName, description;
    protected boolean repeatable, active;
    protected Set<String> requiredQuestObjects, optionalQuestObjects;
    protected Set<QuestRequirement> otherRequirements;
    
    protected QuestObject(Builder<?, ?> builder) {
        questManager = builder.questManager;
        id = builder.id;
        displayName = builder.displayName;
        description = builder.description;
        repeatable = builder.repeatable;
        active = builder.active;
        requiredQuestObjects = new HashSet<>(builder.requiredQuestObjects);
        optionalQuestObjects = new HashSet<>(builder.optionalQuestObjects);
        otherRequirements = new HashSet<>(builder.otherRequirements);
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
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public void addRequiredQuestObject(QuestObject questObject) {
        this.requiredQuestObjects.add(questObject.getId());
    }
    
    public void addOptionalQuestObject(QuestObject questObject) {
        this.optionalQuestObjects.add(questObject.getId());
    }
    
    public void addRequirement(QuestRequirement requirement) {
        this.otherRequirements.add(requirement);
    }
    
    public abstract boolean isComplete(UUID player);
    
    public boolean isAvailable(UUID player) {
        if (!isActive()) {
            return false;
        }
        
        if (isComplete(player)) {
            return false;
        }
        
        if (requiredQuestObjects != null && !requiredQuestObjects.isEmpty()) {
            for (String id : this.requiredQuestObjects) {
                QuestObject questObject = questManager.get(id);
                if (questObject != null) {
                    if (!questObject.isComplete(player)) {
                        return false;
                    }
                }
            }
        }
        
        if (this.otherRequirements != null && !this.otherRequirements.isEmpty()) {
            for (QuestRequirement requirement : this.otherRequirements) {
                if (!requirement.checkSatisfies(player)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    protected static abstract class Builder<Q extends QuestObject, B extends QuestObject.Builder<Q, B>> {
        protected final QuestManager questManager;
        protected String id;
        protected String displayName, description;
        protected boolean repeatable, active;
        protected Set<String> requiredQuestObjects = new HashSet<>(), optionalQuestObjects = new HashSet<>();
        protected Set<QuestRequirement> otherRequirements = new HashSet<>();
        public Builder(QuestManager questManager, String id) {
            this.questManager = questManager;
            this.id = id;
        }
        
        public B id(String id) {
            this.id = id;
            return (B) this;
        }
    
        public B displayName(String displayName) {
            this.displayName = displayName;
            return (B) this;
        }
    
        public B description(String description) {
            this.description = description;
            return (B) this;
        }
    
        public B repeatable(boolean repeatable) {
            this.repeatable = repeatable;
            return (B) this;
        }
    
        public B active(boolean active) {
            this.active = active;
            return (B) this;
        }
        
        public B addRequiredQuestObject(QuestObject object) {
            this.requiredQuestObjects.add(object.getId());
            return (B) this;
        }
    
        public B addOptionalQuestObject(QuestObject object) {
            this.optionalQuestObjects.add(object.getId());
            return (B) this;
        }
        
        public B addRequirement(QuestRequirement requirement) {
            this.otherRequirements.add(requirement);
            return (B) this;
        }
        
        public abstract Q build();
    }
}
