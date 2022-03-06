package com.starmediadev.plugins.starquests.objects.actions;

import com.starmediadev.plugins.starquests.QuestManager;
import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.data.QuestData;
import com.starmediadev.plugins.starquests.storage.StorageHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;

import java.lang.reflect.Method;

public abstract class EventAmountAction<K, D extends QuestData, E extends Event> extends QuestAction<E> {
    
    protected K type;
    protected int amount;
    
    public EventAmountAction(String actionId, K type, int amount) {
        super(actionId);
        this.type = type;
        this.amount = amount;
    }
    
    protected abstract int handleEvent(E event, Quest quest, QuestObjective questObjective, StorageHandler storageHandler, D questData);
    
    @Override
    public void onAction(E e, Quest quest, QuestObjective questObjective) {
        QuestManager questManager = quest.getQuestManager();
        StorageHandler storageHandler = questManager.getStorageHandler();
        Player player = null;
        try {
            if (e instanceof EntityDeathEvent ede) {
                player = ede.getEntity().getKiller();
            } else {
                for (Method method : e.getClass().getDeclaredMethods()) {
                    Class<?> returnType = method.getReturnType();
                    if (returnType != null) {
                        if (Player.class.getName().equals(returnType.getName())) {
                            if (method.getParameters().length == 0) {
                                player = (Player) method.invoke(e);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        
        if (player == null) {
            throw new RuntimeException("There was a problem getting the player in an EventAmountAction of type " + getClass());
        }
        
        if (quest.isAvailable(player.getUniqueId())) {
            D questData = null;
            try {
                questData = (D) storageHandler.getQuestData(player.getUniqueId(), quest, questObjective);
            } catch (ClassCastException ex) {
            }
            
            int currentProgress = handleEvent(e, quest, questObjective, storageHandler, questData);
            
            if (currentProgress >= amount) {
                if (!questObjective.isComplete(player.getUniqueId())) {
                    questObjective.complete(player.getUniqueId());
                }
            }
        }
    }
}