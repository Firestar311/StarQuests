package com.starmediadev.plugins.starquests.objects.actions;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;

public abstract class QuestAction<T> {
    protected final String actionId;
    
    public QuestAction(String actionId) {
        this.actionId = actionId;
    }
    
    public abstract void onAction(T object, Quest quest, QuestObjective questObjective);
}
