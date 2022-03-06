package com.starmediadev.plugins.starquests.cmds;

import com.starmediadev.plugins.starquests.QuestManager;
import com.starmediadev.plugins.starquests.QuestUtils;
import com.starmediadev.plugins.starquests.StarQuests;
import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestLine;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.actions.BlockBreakAction;
import com.starmediadev.plugins.starquests.objects.actions.EntityKillAction;
import com.starmediadev.plugins.starquests.objects.rewards.ItemReward;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Dependency;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Command("questadmin")
@CommandPermission("starquests.command.admin")
public class QuestAdminCmds {
    @Dependency
    private StarQuests plugin;
    
    @Subcommand("test create")
    public void createTest(Player player) {
        QuestManager questManager = plugin.getQuestManager();
        QuestLine.Builder questLineBuilder = new QuestLine.Builder(questManager, QuestUtils.generateQuestLineId()).active(true).description("A questline for testing.")
                .displayName("Test Quest Line");
        QuestLine questLine = questLineBuilder.build();
        questManager.add(questLine);
    
        Quest.Builder questBuilder = new Quest.Builder(questManager, QuestUtils.generateQuestId()).active(true);
        questBuilder.description("The first of the test quests").displayName("First Quest").addReward(new ItemReward.Builder().id("goldreward").displayName("10 Gold")
                .itemStack(new ItemStack(Material.GOLD_INGOT, 10)).build());
        
        QuestObjective.Builder objectiveBuilder = new QuestObjective.Builder(QuestUtils.generateObjectiveId());
        questBuilder.addObjective(objectiveBuilder.displayName("Break 10 Stone").action(new BlockBreakAction(Material.STONE, 10)).build());
        questBuilder.addObjective(objectiveBuilder.id(QuestUtils.generateObjectiveId()).displayName("Break 20 Dirt").action(new BlockBreakAction(Material.DIRT, 20)).build());
    
        Quest firstQuest = questBuilder.build();
        questManager.add(firstQuest);
        questLine.addQuest(firstQuest);
        
        questBuilder.clearObjectives().clearRewards().id(QuestUtils.generateQuestId()).displayName("Second Quest").description("The second test quest.")
                .addObjective(objectiveBuilder.id(QuestUtils.generateObjectiveId()).displayName("Kill 20 Zombies").action(new EntityKillAction(EntityType.ZOMBIE, 20)).build());
        Quest secondQuest = questBuilder.build();
        secondQuest.addRequiredQuestObject(firstQuest);
        questManager.add(secondQuest);
        questLine.addQuest(secondQuest);
    }
}