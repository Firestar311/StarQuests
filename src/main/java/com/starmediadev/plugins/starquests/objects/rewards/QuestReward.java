package com.starmediadev.plugins.starquests.objects.rewards;

import org.bukkit.entity.Player;

/**
 * Represents a reward for completing a quest object
 */
public abstract class QuestReward {
    /**
     * An ID for the reward
     */
    protected String id;
    /**
     * The name of the reward
     */
    protected String name;
    /**
     * The title of the reward. This is the one displayed
     */
    protected String title;
    
    /**
     * Constructs a new QuestReward
     * @param id The id for the reward
     */
    public QuestReward(String id) {
        this.id = id;
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
}