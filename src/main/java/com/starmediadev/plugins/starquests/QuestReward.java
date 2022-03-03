package com.starmediadev.plugins.starquests;

import org.bukkit.entity.Player;

public abstract class QuestReward {
    protected final String id;
    protected String displayName;
    
    public QuestReward(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
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
    
    abstract void applyReward(Player player) throws Exception;
}