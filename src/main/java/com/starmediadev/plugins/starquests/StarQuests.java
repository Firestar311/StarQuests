package com.starmediadev.plugins.starquests;

import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class StarQuests extends JavaPlugin {

    private QuestManager questManager;
    
    public void onEnable() {
        questManager = new QuestManager();
        getServer().getServicesManager().register(QuestManager.class, questManager, this, ServicePriority.Highest);
    }
    
    public QuestManager getQuestManager() {
        return questManager;
    }
}