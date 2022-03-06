package com.starmediadev.plugins.starquests.objects.data;

import java.util.UUID;

public class AmountQuestData extends QuestData {
    private int amount;
    
    public AmountQuestData(String questId, String questObjectiveId, UUID uniqueId) {
        super(questId, questObjectiveId, uniqueId);
    }
    
    public void increment() {
        amount++;
    }
    
    public int getAmount() {
        return amount;
    }
    
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
