package com.starmediadev.plugins.starquests.objects.registry;

import com.starmediadev.plugins.starquests.QuestManager;
import com.starmediadev.plugins.starquests.QuestUtils;
import com.starmediadev.plugins.starquests.objects.Quest;

public class QuestRegistry extends QuestObjectRegistry<Quest> {
    public QuestRegistry(QuestManager questManager) {
        super(questManager);
    }
    
    @Override
    public boolean isValidId(String id) {
        return QuestUtils.isQuestId(id);
    }
}
