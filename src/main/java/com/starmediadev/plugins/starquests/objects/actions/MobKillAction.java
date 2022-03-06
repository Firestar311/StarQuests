package com.starmediadev.plugins.starquests.objects.actions;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.data.MobKillQuestData;
import com.starmediadev.plugins.starquests.storage.StorageHandler;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Map;

public class MobKillAction extends EventAmountAction<EntityType, MobKillQuestData, EntityDeathEvent> {
    public MobKillAction(Map<EntityType, Integer> requiredAmounts) {
        super("mobkill", requiredAmounts);
    }
    
    @Override
    protected Map<EntityType, Integer> handleEvent(EntityDeathEvent event, Quest quest, QuestObjective questObjective, StorageHandler storageHandler, MobKillQuestData questData) {
        if (questData == null) {
            questData = new MobKillQuestData(quest.getId(), questObjective.getId(), event.getEntity().getKiller().getUniqueId());
        }
        storageHandler.addQuestData(event.getEntity().getKiller().getUniqueId(), questData);
    
        EntityType type = event.getEntityType();
        if (this.requiredAmounts.containsKey(type)) {
            questData.addEntityKilled(type);
        }
        
        return questData.getMobsKilled();
    }
}
