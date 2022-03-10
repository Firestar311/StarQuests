package com.starmediadev.plugins.starquests.objects;

import java.util.UUID;

public abstract class QuestRequirement {
    private String title;
    
    public abstract boolean checkSatisfies(UUID player);
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
}
