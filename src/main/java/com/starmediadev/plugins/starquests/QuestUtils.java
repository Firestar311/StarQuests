package com.starmediadev.plugins.starquests;

import com.starmediadev.utils.Utils;

public final class QuestUtils {
    
    public static final int QUEST_LINE_ID_LENGTH = 10, QUEST_POOL_ID_LENGTH = 8, QUEST_ID_LENGTH = 6;
    
    public static String generateQuestLineId() {
        return Utils.generateCode(QUEST_LINE_ID_LENGTH, false, true, false);
    }
    
    public static String generateQuestPoolId() {
        return Utils.generateCode(QUEST_POOL_ID_LENGTH, false, true, false);
    }
    
    public static String generateQuestId() {
        return Utils.generateCode(QUEST_ID_LENGTH, false, true, false);
    }
    
    public static boolean isQuestLineId(String id) {
        return id.length() == QUEST_LINE_ID_LENGTH;
    }
    
    public static boolean isQuestPoolId(String id) {
        return id.length() == QUEST_POOL_ID_LENGTH;
    }
    
    public static boolean isQuestId(String id) {
        if (id.contains("_")) {
            String[] idSplit = id.split("_");
            return idSplit[1].length() == QUEST_ID_LENGTH;
        } else {
            return id.length() == QUEST_ID_LENGTH;       
        }
    }
}
