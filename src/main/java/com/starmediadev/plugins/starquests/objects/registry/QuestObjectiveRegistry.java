package com.starmediadev.plugins.starquests.objects.registry;

import com.starmediadev.plugins.starquests.QuestManager;
import com.starmediadev.plugins.starquests.QuestUtils;
import com.starmediadev.plugins.starquests.objects.QuestObjective;

public class QuestObjectiveRegistry extends QuestObjectRegistry<QuestObjective> {
    /**
     * {@inheritDoc}
     */
    public QuestObjectiveRegistry(QuestManager questManager) {
        super(questManager);
    }
    
    @Override
    protected String generateId() {
        return QuestUtils.generateObjectiveId();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValidId(String id) {
        return QuestUtils.isObjectiveId(id);
    }
}
