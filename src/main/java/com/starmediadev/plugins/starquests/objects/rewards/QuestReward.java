package com.starmediadev.plugins.starquests.objects.rewards;

import org.bukkit.entity.Player;

public abstract class QuestReward {
    protected final String id;
    protected String displayName;
    
    protected QuestReward(Builder<?, ?> builder) {
        this.id = builder.id;
        this.displayName = builder.displayName;
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
    
    public abstract void applyReward(Player player) throws Exception;
    
    protected static abstract class Builder<T extends QuestReward, B extends QuestReward.Builder<T, B>> {
        protected String id, displayName;
        
        public B id(String id) {
            this.id = id;
            return (B) this;
        }
        
        public B displayName(String displayName) {
            this.displayName = displayName;
            return (B) this;
        }
        
        public abstract T build();
    }
}