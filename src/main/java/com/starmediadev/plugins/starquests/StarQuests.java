package com.starmediadev.plugins.starquests;

import com.starmediadev.plugins.starquests.storage.StorageHandler;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class StarQuests extends JavaPlugin {
    
    private static StarQuests instance;
    
    public static StarQuests getInstance() {
        return instance;
    }
    
    private QuestManager questManager;
    
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        StorageHandler storageHandler = new StorageHandler();
        questManager = new QuestManager(storageHandler);
        getServer().getServicesManager().register(QuestManager.class, questManager, this, ServicePriority.Highest);
    }
    
    public QuestManager getQuestManager() {
        return questManager;
    }
}