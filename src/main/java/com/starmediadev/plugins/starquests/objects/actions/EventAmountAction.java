package com.starmediadev.plugins.starquests.objects.actions;

import com.starmediadev.plugins.starquests.QuestManager;
import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.data.AmountQuestData;
import com.starmediadev.plugins.starquests.storage.StorageHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class for actions based on an amount of an event. This can be any event
 *
 * @param <K> The type to be completed
 * @param <E> The bukkit event type
 */
public abstract class EventAmountAction<K, E extends Event> extends QuestAction<E> {
    
    /**
     * This is the types of thing being tracked
     */
    protected List<K> types = new ArrayList<>();
    /**
     * The amount that needs to reached
     */
    protected int amount;
    
    /**
     * Constructs an EventAmountAction
     *
     * @param actionId Used internally, should be provided in the constructor of the child classes
     * @param type     The type to be tracked
     * @param amount   The amount to be reached
     */
    public EventAmountAction(String actionId, K type, int amount) {
        super(actionId);
        this.types.add(type);
        this.amount = amount;
    }
    
    /**
     * Constructs an EventAmountAction
     *
     * @param actionId Used internally, should be provided in the constructor of the child classes
     * @param types    The types to be tracked, these are grouped together as if it is one
     * @param amount   The amount to be reached
     */
    public EventAmountAction(String actionId, List<K> types, int amount) {
        super(actionId);
        this.types = types;
        this.amount = amount;
    }
    
    /**
     * This method is called by the onAction method to make handling of these a little cleaner
     *
     * @param event          The Bukkit Event
     * @param player         The player
     * @param quest          The quest that is being referred to
     * @param questObjective The objective
     * @param storageHandler The storage handler
     * @param questData      The existing quest data. If it doesn't exist, it will be created
     */
    protected abstract void handleEvent(E event, Player player, Quest quest, QuestObjective questObjective, StorageHandler storageHandler, AmountQuestData questData);
    
    /**
     * Overridden from QuestAction, handles what happens when an action happens
     *
     * @param e              The Bukkit Event
     * @param player         The player
     * @param quest          The quest
     * @param questObjective The objective
     */
    @Override
    public void onAction(E e, Player player, Quest quest, QuestObjective questObjective) {
        QuestManager questManager = quest.getQuestManager();
        StorageHandler storageHandler = questManager.getStorageHandler();
        
        AmountQuestData questData = null;
        try {
            questData = (AmountQuestData) storageHandler.getQuestData(player.getUniqueId(), quest, questObjective);
        } catch (ClassCastException ex) {
        }
        
        if (questData == null) {
            questData = new AmountQuestData(quest.getId(), questObjective.getId(), player.getUniqueId());
            storageHandler.addQuestData(player.getUniqueId(), questData);
        }
        
        handleEvent(e, player, quest, questObjective, storageHandler, questData);
        
        if (questData.getAmount() >= amount) {
            if (!questObjective.isComplete(player.getUniqueId())) {
                questObjective.complete(player.getUniqueId());
            }
        }
    }
}