package com.starmediadev.plugins.starquests.objects.registry;

import com.starmediadev.plugins.starquests.QuestManager;
import com.starmediadev.plugins.starquests.QuestUtils;
import com.starmediadev.plugins.starquests.objects.Quest;

/**
 * Registry for quests
 */
public class QuestRegistry extends QuestObjectRegistry<Quest> {
    /**
     * {@inheritDoc}
     */
    public QuestRegistry(QuestManager questManager) {
        super(questManager);
    }
    
    @Override
    protected String generateId() {
        return QuestUtils.generateQuestId();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValidId(String id) {
        return QuestUtils.isQuestId(id);
    }
}
