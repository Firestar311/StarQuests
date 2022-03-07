package com.starmediadev.plugins.starquests.objects.rewards;

import com.starmediadev.plugins.starmcutils.util.ColorUtils;
import org.bukkit.entity.Player;

public abstract class QuestReward {
    protected final String id;
    protected String name, title;
    
    protected QuestReward(Builder<?, ?> builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.name = builder.name;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public abstract void applyReward(Player player) throws Exception;
    
    protected static abstract class Builder<T extends QuestReward, B extends QuestReward.Builder<T, B>> {
        protected String id, title, name;
        
        public B id(String id) {
            this.id = id;
            return (B) this;
        }
        
        public B title(String title) {
            this.title = title;
            return (B) this;
        }
        
        public B nameFromTitle() {
            this.name = title.toLowerCase().replace(" ", "_");
            this.name = ColorUtils.stripColor(this.name);
            return (B) this;
        }
        
        public B name(String name) {
            this.name = name;
            return (B) this;
        }
        
        public abstract T build();
    }
}