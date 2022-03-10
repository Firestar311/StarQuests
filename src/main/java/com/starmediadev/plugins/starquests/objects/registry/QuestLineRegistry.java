package com.starmediadev.plugins.starquests.objects.registry;

import com.starmediadev.plugins.starquests.QuestManager;
import com.starmediadev.plugins.starquests.QuestUtils;
import com.starmediadev.plugins.starquests.objects.QuestLine;

public class QuestLineRegistry extends QuestObjectRegistry<QuestLine> {
    public QuestLineRegistry(QuestManager questManager) {
        super(questManager);
    }
    
    @Override
    public boolean isValidId(String id) {
        return QuestUtils.isQuestLineId(id);
    }
}
