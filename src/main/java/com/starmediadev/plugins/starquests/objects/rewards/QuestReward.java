package com.starmediadev.plugins.starquests.objects.rewards;

import com.starmediadev.plugins.starmcutils.util.ColorUtils;
import com.starmediadev.plugins.starquests.QuestManager;
import com.starmediadev.plugins.starquests.objects.QuestObject;
import org.bukkit.entity.Player;

import java.util.Objects;

/**
 * Represents a reward for completing a quest object
 */
public abstract class QuestReward {
    protected String id, name, title;
    protected QuestManager questManager;
    protected QuestObject questObject;
    
    /**
     * Constructs a new QuestReward
     * @param id The id for the reward
     */
    public QuestReward(String id, String title, QuestObject questObject) {
        this.id = id;
        this.title = title;
        this.questObject = questObject;
    }
    
    public QuestReward(String title, QuestObject questObject) {
        this.title = title;
        this.questObject = questObject;
    }
    
    /**
     * Gets the id
     * @return the id
     */
    public String getId() {
        return id;
    }
    
    /**
     * gets the name 
     * @return the name
     */
    public String getName() {
        if (name == null || name.equals("")) {
            this.name = ColorUtils.stripColor(getTitle().toLowerCase().replace(" ", "_"));
        }
        return name;
    }
    
    /**
     * Sets the name
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the title 
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Sets the title
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Applies the reward to an online player
     * @param player The player
     * @throws Exception Any exceptions thrown from applying this reward
     */
    public abstract void applyReward(Player player) throws Exception;
    
    public void setId(String id) {
        this.id = id;
    }
    
    public void setQuestManager(QuestManager questManager) {
        this.questManager = questManager;
    }
    
    public QuestObject getQuestObject() {
        return questObject;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        QuestReward reward = (QuestReward) o;
        return Objects.equals(id, reward.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}