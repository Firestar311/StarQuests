package com.starmediadev.plugins.starquests.objects;

import com.starmediadev.plugins.starmcutils.util.ColorUtils;
import com.starmediadev.plugins.starquests.QuestManager;
import com.starmediadev.plugins.starquests.objects.rewards.QuestReward;

import java.util.*;

/**
 * The parent object for all base quest based objects that have an action of sorts
 */
public abstract class QuestObject implements Comparable<QuestObject> {
    protected String id;
    protected QuestManager questManager;
    protected String title, name, description;
    protected boolean repeatable, active = true;
    protected List<QuestObject> prerequisiteObjects = new ArrayList<>(), sideQuestObjects = new ArrayList<>();
    protected List<QuestRequirement> requirements = new ArrayList<>();
    protected List<QuestReward> rewards = new ArrayList<>();
    
    public QuestObject(String id, String title) {
        this.id = id;
        this.title = title;
    }
    
    public QuestObject(String title) {
        this.title = title;
    }
    
    public QuestManager getQuestManager() {
        return questManager;
    }
    
    public void setQuestManager(QuestManager questManager) {
        this.questManager = questManager;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        if (description == null || description.equals("")) {
            return "No Description";
        }
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
    
    public List<QuestReward> getRewards() {
        return rewards;
    }
    
    public void setRewards(List<QuestReward> rewards) {
        this.rewards = rewards;
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
    
    public List<QuestRequirement> getRequirements() {
        return requirements;
    }
    
    public void setRequirements(List<QuestRequirement> requirements) {
        this.requirements = requirements;
    }
    
    public List<QuestObject> getSideQuestObjects() {
        return sideQuestObjects;
    }
    
    public void setSideQuestObjects(List<QuestObject> sideQuestObjects) {
        this.sideQuestObjects = sideQuestObjects;
    }
    
    public List<QuestObject> getPrerequisiteObjects() {
        return prerequisiteObjects;
    }
    
    public void setPrerequisiteObjects(List<QuestObject> prerequisiteObjects) {
        this.prerequisiteObjects = prerequisiteObjects;
    }
    
    public abstract void complete(UUID uniqueId);
    
    public void addReward(QuestReward reward) {
        this.rewards.add(reward);
    }
    
    public List<QuestObject> getAllPrerequisites() {
        List<QuestObject> prerequisites = new ArrayList<>();
        for (QuestObject prerequisiteObject : this.prerequisiteObjects) {
            prerequisites.add(prerequisiteObject);
            prerequisites.addAll(prerequisiteObject.getAllPrerequisites());
        }
        return prerequisites;
    }
    
    @Override
    public int compareTo(QuestObject o) {
        for (QuestObject prerequisite : getAllPrerequisites()) {
            if (prerequisite.getId().equals(o.getId())) {
                return 1;
            }
        }
        
        return -1;
    }
}
