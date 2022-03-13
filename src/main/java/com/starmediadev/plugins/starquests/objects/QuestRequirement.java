package com.starmediadev.plugins.starquests.objects;

import java.util.UUID;

public abstract class QuestRequirement {
    /**
     * The title must be something that would make sense when displayed via a command or a gui, for example
     * You must {title}
     */
    protected String title;
    
    public abstract boolean checkSatisfies(UUID player);
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
}
