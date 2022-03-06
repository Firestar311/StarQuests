package com.starmediadev.plugins.starquests.objects.actions;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.data.AmountQuestData;
import com.starmediadev.plugins.starquests.storage.StorageHandler;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityKillAction extends EventAmountAction<EntityType, AmountQuestData, EntityDeathEvent> {
    public EntityKillAction(EntityType type, int amount) {
        super("entitykill", type, amount);
    }
    
    @Override
    protected int handleEvent(EntityDeathEvent event, Quest quest, QuestObjective questObjective, StorageHandler storageHandler, AmountQuestData questData) {
        if (questData == null) {
            questData = new AmountQuestData(quest.getId(), questObjective.getId(), event.getEntity().getKiller().getUniqueId());
        }
    
        storageHandler.addQuestData(event.getEntity().getKiller().getUniqueId(), questData);
    
        EntityType type = event.getEntityType();
        if (this.type == type) {
            questData.increment();
        }
        
        return questData.getAmount();
    }
}
