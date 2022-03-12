package com.starmediadev.plugins.starquests.cmds;

import com.starmediadev.plugins.starmcutils.util.MCUtils;
import com.starmediadev.plugins.starquests.QuestManager;
import com.starmediadev.plugins.starquests.StarQuests;
import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestLine;
import com.starmediadev.plugins.starquests.objects.QuestObject;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.rewards.QuestReward;
import com.starmediadev.utils.helper.StringHelper;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Dependency;
import revxrsal.commands.annotation.Named;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import revxrsal.commands.exception.CommandErrorException;

import java.util.List;

public class QuestCmds {
    
    @Dependency
    private StarQuests plugin;
    
    @Command("quest")
    @CommandPermission("starquests.command.quest")
    public void quest(Player player, @Named("questId") @Optional String questId) {
        QuestManager questManager = plugin.getQuestManager();
        List<QuestLine> questLines = questManager.getQuestLines();
        if (questLines.isEmpty()) {
            throw new CommandErrorException("No quest lines exist");
        }
        
        QuestLine questLine = questLines.get(0);
        List<Quest> availableQuests = questLine.getAvailableQuests(player.getUniqueId());
        Quest quest = null;
        if (availableQuests.size() > 0) {
            if (questId == null) {
                if (availableQuests.size() > 1) {
                    List<String> questIds = availableQuests.stream().map(QuestObject::getId).toList();
                    throw new CommandErrorException("You have multiple quests available, please use /quest <questId> to view a specific quest: " + questIds);
                }
                
                quest = availableQuests.get(0);
            } else {
                for (Quest availableQuest : availableQuests) {
                    if (availableQuest.getId().equalsIgnoreCase(questId)) {
                        quest = availableQuest;
                    }
                }
            }
        }
        
        if (quest == null) {
            throw new CommandErrorException("Could not find an available quest.");
        }
        
        player.sendMessage(MCUtils.color("Quest: " + quest.getTitle()));
        if (!StringHelper.isEmpty(quest.getDescription())) {
            player.sendMessage(MCUtils.color("Description: " + quest.getDescription()));
        }
        
        player.sendMessage(MCUtils.color("Objectives"));
        for (QuestObjective objective : quest.getObjectives()) {
            player.sendMessage(MCUtils.color("- " + ((objective.isComplete(player.getUniqueId()) ? "&a" : "&c")) + objective.getTitle()));
            if (!StringHelper.isEmpty(objective.getDescription())) {
                player.sendMessage(MCUtils.color("  Description: " + objective.getDescription()));
            }
        }
        
        player.sendMessage(MCUtils.color("Rewards"));
        for (QuestReward reward : quest.getRewards()) {
            player.sendMessage(MCUtils.color("- " + reward.getTitle()));
        }
    }
}
