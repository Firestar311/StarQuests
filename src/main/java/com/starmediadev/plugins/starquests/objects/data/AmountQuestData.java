package com.starmediadev.plugins.starquests.objects.data;

import java.util.UUID;

/**
 * This class represents quest data based on an amount. The handling of the action should be handled by the QuestAction class. this just stores the data
 * Each quest objective has a single action
 */
public class AmountQuestData extends QuestData {
    /**
     * The current amount by the player
     */
    private int amount;
    
    /**
     * {@inheritDoc}
     */
    public AmountQuestData(String questId, String questObjectiveId, UUID uniqueId) {
        super(questId, questObjectiveId, uniqueId);
    }
    
    /**
     * Used internally by reflection
     */
    private AmountQuestData() {
    }
    
    /**
     * Increments the amount by 1
     */
    public void increment() {
        amount++;
    }
    
    /**
     * Gets the current amount
     * @return The current amount
     */
    public int getAmount() {
        return amount;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "AmountQuestData{" +
                "amount=" + amount +
                ", questId='" + questId + '\'' +
                ", questObjectiveId='" + questObjectiveId + '\'' +
                ", uniqueId=" + uniqueId +
                '}';
    }
}
