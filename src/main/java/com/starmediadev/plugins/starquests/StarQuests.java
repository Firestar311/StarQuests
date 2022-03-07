package com.starmediadev.plugins.starquests;

import com.starmediadev.plugins.starquests.cmds.QuestCommands;
import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestLine;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.actions.BlockBreakAction;
import com.starmediadev.plugins.starquests.objects.actions.EntityKillAction;
import com.starmediadev.plugins.starquests.objects.rewards.ItemReward;
import com.starmediadev.plugins.starquests.storage.StorageHandler;
import com.starmediadev.plugins.starquests.storage.YamlStorageHandler;
import com.starmediadev.plugins.starquests.cmds.QuestAdminCmds;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.CommandHandler;
import revxrsal.commands.bukkit.BukkitCommandHandler;

public class StarQuests extends JavaPlugin {
    
    private static StarQuests instance;
    
    public static StarQuests getInstance() {
        return instance;
    }
    
    private QuestManager questManager;
    
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        StorageHandler storageHandler = new YamlStorageHandler();
        questManager = new QuestManager(storageHandler);
        getServer().getServicesManager().register(QuestManager.class, questManager, this, ServicePriority.Highest);
        getServer().getPluginManager().registerEvents(new ActionListener(), this);
    
        CommandHandler commandHandler = BukkitCommandHandler.create(this);
        commandHandler.registerDependency(StarQuests.class, this);
        commandHandler.register(new QuestAdminCmds());
        commandHandler.register(new QuestCommands());
        
        storageHandler.setup();
        storageHandler.loadData();
    
        Bukkit.getScheduler().runTaskTimer(this, storageHandler::saveData, 1L, 6000L);
    
        QuestLine.Builder questLineBuilder = new QuestLine.Builder(questManager, QuestUtils.generateQuestLineId()).active(true).description("A questline for testing.")
                .displayName("Test Quest Line");
        QuestLine questLine = questLineBuilder.build();
        questManager.add(questLine);
    
        Quest.Builder questBuilder = new Quest.Builder(questManager, "abcdef").active(true);
        questBuilder.description("The first of the test quests").displayName("First Quest").addReward(new ItemReward.Builder().id("goldreward").displayName("10 Gold")
                .itemStack(new ItemStack(Material.GOLD_INGOT, 10)).build());
    
        QuestObjective.Builder objectiveBuilder = new QuestObjective.Builder("ghigklmnopkr");
        questBuilder.addObjective(objectiveBuilder.displayName("Break 5 Stone").action(new BlockBreakAction(Material.STONE, 5)).build());
        questBuilder.addObjective(objectiveBuilder.id("stuvwzyzaabb").displayName("Break 5 Dirt").action(new BlockBreakAction(Material.DIRT, 5)).build());
    
        Quest firstQuest = questBuilder.build();
        questManager.add(firstQuest);
        questLine.addQuest(firstQuest);
    
        questBuilder.clearObjectives().clearRewards().id("ccddee").displayName("Second Quest").description("The second test quest.")
                .addObjective(objectiveBuilder.id("ffgghhiijjkk").displayName("Kill 5 Zombies").action(new EntityKillAction(EntityType.ZOMBIE, 5)).build());
        Quest secondQuest = questBuilder.build();
        secondQuest.addRequiredQuestObject(firstQuest);
        questManager.add(secondQuest);
        questLine.addQuest(secondQuest);
    }
    
    @Override
    public void onDisable() {
        questManager.getStorageHandler().saveData();
    }
    
    public QuestManager getQuestManager() {
        return questManager;
    }
}