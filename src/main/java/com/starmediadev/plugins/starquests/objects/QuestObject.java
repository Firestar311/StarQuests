package com.starmediadev.plugins.starquests.objects;

import com.starmediadev.plugins.starquests.QuestManager;
import com.starmediadev.plugins.starquests.objects.interfaces.QuestRequirement;
import org.bukkit.entity.Player;

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
    protected Set<String> requiredQuestObjects = new HashSet<>(), optionalQuestObjects = new HashSet<>();
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
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public abstract boolean isComplete(UUID player);
    
    public boolean isAvailable(Player player) {
        if (!isActive()) {
            return true;
        }
        if (isComplete(player.getUniqueId())) {
            return false;
        }
        for (String id : this.requiredQuestObjects) {
            QuestObject questObject = questManager.get(id);
            if (questObject != null) {
                if (!questObject.isComplete(player.getUniqueId())) {
                    return false;
                }
            }
        }
        
        for (QuestRequirement requirement : this.requiredOtherRequirements) {
            if (!requirement.checkSatisfies(player)) {
                return false;
            }
        }
        return true;
    }
}
