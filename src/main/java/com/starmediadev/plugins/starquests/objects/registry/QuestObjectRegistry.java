package com.starmediadev.plugins.starquests.objects.registry;

import com.starmediadev.plugins.starquests.QuestManager;
import com.starmediadev.plugins.starquests.objects.QuestObject;
import com.starmediadev.plugins.starquests.objects.exceptions.QuestObjectRegistryException;
import com.starmediadev.utils.helper.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.*;

public abstract class QuestObjectRegistry<Q extends QuestObject> {
    protected QuestManager questManager;
    protected Map<String, Q> registeredObjects = new HashMap<>();
    
    public QuestObjectRegistry(QuestManager questManager) {
        this.questManager = questManager;
    }
    
    public void register(Q questObject) {
        Set<Field> fields = ReflectionHelper.getClassFields(questObject.getClass());
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType().equals(QuestManager.class)) {
                try {
                    field.set(questObject, questManager);
                } catch (IllegalAccessException e) {
                    throw new QuestObjectRegistryException("Could not register quest object " + questObject.getId() + " because the quest manager field could not be set " + e.getMessage());
                }
            }
        }
    
        registeredObjects.put(questObject.getId(), questObject);
    }
    
    public abstract boolean isValidId(String id);
    
    public Q get(String identifier) {
        if (isValidId(identifier)) {
            return registeredObjects.get(identifier);
        } else {
            for (Q value : registeredObjects.values()) {
                String name = value.getName();
                if (identifier.equalsIgnoreCase(name)) {
                    return value;
                }
            }
        }
        return null;
    }
    
    public List<Q> getAllRegistered() {
        return new ArrayList<>(registeredObjects.values());
    }
    
    public QuestManager getQuestManager() {
        return questManager;
    }
}
