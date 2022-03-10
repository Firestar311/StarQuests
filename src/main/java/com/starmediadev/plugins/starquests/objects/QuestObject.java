package com.starmediadev.plugins.starquests.objects;

import com.starmediadev.plugins.starmcutils.util.ColorUtils;
import com.starmediadev.plugins.starmcutils.util.MCUtils;
import com.starmediadev.plugins.starquests.QuestManager;
import com.starmediadev.plugins.starquests.StarQuests;
import com.starmediadev.plugins.starquests.objects.rewards.QuestReward;
import com.starmediadev.plugins.starquests.storage.StorageHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * The parent object for all base quest based objects that have an action of sorts
 */
public abstract class QuestObject {
    protected final String id;
    protected QuestManager questManager;
    protected String title, name, description;
    protected boolean repeatable, active;
    protected final Set<QuestObject> prerequisiteObjects = new HashSet<>(), sideQuestObjects = new HashSet<>();
    protected final Set<QuestRequirement> requirements = new HashSet<>();
    protected final Set<QuestReward> rewards = new HashSet<>();
    
    public QuestObject(String id) {
        this.id = id;
    }
    
    public final QuestManager getQuestManager() {
        return questManager;
    }
    
    public String getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
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
    
    public void addPrerequisite(QuestObject questObject) {
        this.prerequisiteObjects.add(questObject);
    }
    
    public void addSideQuestObject(QuestObject questObject) {
        this.sideQuestObjects.add(questObject);
    }
    
    public void addRequirement(QuestRequirement requirement) {
        this.requirements.add(requirement);
    }
    
    public abstract boolean isComplete(UUID player);
    
    public String getName() {
        if (name == null || name.equals("")) {
            this.name = ColorUtils.stripColor(getTitle().toLowerCase().replace(" ", "_"));
        }
        return name;
    }
    
    public QuestObject setName(String name) {
        this.name = ColorUtils.stripColor(name);
        return this;
    }
    
    public Set<QuestReward> getRewards() {
        return new HashSet<>(rewards);
    }
    
    public void setRewards(List<QuestReward> rewards) {
        this.rewards.clear();
        this.rewards.addAll(rewards);
    }
    
    public boolean meetsPrequisites(UUID player) {
        if (prerequisiteObjects != null && !prerequisiteObjects.isEmpty()) {
            for (QuestObject questObject : this.prerequisiteObjects) {
                if (!questObject.isComplete(player)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean meetsRequirements(UUID player) {
        if (this.requirements != null && !this.requirements.isEmpty()) {
            for (QuestRequirement requirement : this.requirements) {
                if (!requirement.checkSatisfies(player)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean isAvailable(UUID player) {
        if (!isActive()) {
            return false;
        }
        
        if (isComplete(player)) {
            return false;
        }
        
        if (!meetsPrequisites(player)) {
            return false;
        }
    
        return meetsRequirements(player);
    }
    
    public Set<QuestRequirement> getRequirements() {
        return requirements;
    }
    
    public void setRequirements(List<QuestRequirement> requirements) {
        this.requirements.clear();
        this.requirements.addAll(requirements);
    }
    
    public Set<QuestObject> getSideQuestObjects() {
        return sideQuestObjects;
    }
    
    public void setSideQuestObjects(List<QuestObject> sideQuestObjects) {
        this.sideQuestObjects.clear();
        this.sideQuestObjects.addAll(sideQuestObjects);
    }
    
    public Set<QuestObject> getPrerequisiteObjects() {
        return prerequisiteObjects;
    }
    
    public void setPrerequisiteObjects(List<QuestObject> prerequisiteObjects) {
        this.prerequisiteObjects.clear();
        this.prerequisiteObjects.addAll(prerequisiteObjects);
    }
    
    public abstract void complete(UUID uniqueId);
}
