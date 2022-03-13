package com.starmediadev.plugins.starquests.objects.actions;

import com.starmediadev.plugins.starquests.QuestManager;
import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.data.QuestData;
import com.starmediadev.plugins.starquests.storage.StorageHandler;
import org.bukkit.entity.Player;

/**
 * This represents an action for a QuestObjective
 *
 * @param <T> The action object. This should be something that holds information about the action that occurred
 */
public abstract class QuestAction<T> {
    /**
     * This is the id of the Action. Should be a more better name for the action based on what it is.
     */
    protected final String actionId;
    
    /**
     * Constructs a QusetAction
     *
     * @param actionId The action id
     */
    public QuestAction(String actionId) {
        this.actionId = actionId;
    }
    
    public QuestData getExistingQuestData(Quest quest, QuestObjective questObjective, Player player) {
        QuestManager questManager = quest.getQuestManager();
        StorageHandler storageHandler = questManager.getStorageHandler();
        QuestData questData = null;
        if (quest.isAvailable(player.getUniqueId())) {
            questData = null;
            try {
                questData = storageHandler.getQuestData(player.getUniqueId(), quest, questObjective);
            } catch (ClassCastException ex) {
            }
        }
        return questData;
    }
    
    /**
     * This method is called when an action happens and should be handled by the lower classes
     *
     * @param object         The object of the action
     * @param player         The Player from the Action
     * @param quest          The quest that is being referred to
     * @param questObjective The objective of the action
     */
    public abstract void onAction(T object, Player player, Quest quest, QuestObjective questObjective);
}
