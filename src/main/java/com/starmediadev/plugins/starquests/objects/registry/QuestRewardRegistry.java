package com.starmediadev.plugins.starquests.objects.registry;

import com.starmediadev.plugins.starquests.QuestManager;
import com.starmediadev.plugins.starquests.QuestUtils;
import com.starmediadev.plugins.starquests.objects.rewards.QuestReward;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A registry for quest rewards
 */
public class QuestRewardRegistry {
    /**
     * The quest manager
     */
    protected QuestManager questManager;
    /**
     * All registered objects in this registry object
     */
    protected Map<String, QuestReward> registeredObjects = new HashMap<>();
    
    /**
     * Constructs a new QuestObjectRegistry
     * @param questManager The quest manager to be used
     */
    public QuestRewardRegistry(QuestManager questManager) {
        this.questManager = questManager;
    }
    
    public String createNewId() {
        String id;
        do {
            id = QuestUtils.generateRewardId();
        } while (questManager.getStorageHandler().isRegisteredId(id) || registeredObjects.containsKey(id));
        
        return id;
    }
    
    /**
     * Registers a new quest object
     * @param reward The reward to register
     */
    public void register(QuestReward reward) {
        if (reward.getId() == null || reward.getId().equals("")) {
            reward.setId(createNewId());
        }
    
        if (questManager.getStorageHandler().isRegisteredId(reward.getId())) {
            questManager.getStorageHandler().removeRegisteredId(reward.getId());
        }
        registeredObjects.put(reward.getId(), reward);
    }
    
    /**
     * Checks to see if the provided id is a valid one
     * @param id The id to check
     * @return The 
     */
    public boolean isValidId(String id) {
        return QuestUtils.isRewardId(id);
    }
    
    /**
     * Gets an object
     * @param identifier This is the identifier, either the ID or a name
     * @return The registered object or null if not found
     */
    public QuestReward get(String identifier) {
        QuestReward object = null;
        if (isValidId(identifier)) {
            object = registeredObjects.get(identifier);
        } 
        if (object == null) {
            for (QuestReward value : registeredObjects.values()) {
                String name = value.getName();
                if (identifier.equalsIgnoreCase(name)) {
                    return value;
                }
            }
        }
        return object;
    }
    
    /**
     * Gets all registered objects
     * @return All registered objects
     */
    public List<QuestReward> getAllRegistered() {
        return new ArrayList<>(registeredObjects.values());
    }
    
    /**
     * Gets the referenced questmanager
     * @return The quest manager
     */
    public QuestManager getQuestManager() {
        return questManager;
    }
}
