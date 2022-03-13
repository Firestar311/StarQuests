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
    public void register(Quest quest) {
        super.register(quest);
        if (quest.getQuestLine() != null) {
            quest.getQuestLine().addQuest(quest);
        }
    }
    
    @Override
    protected String getCachedId(Quest object) {
        return questManager.getStorageHandler().getCachedQuestIds().get(object.getQuestLine().getId() + "-" + object.getName());
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
