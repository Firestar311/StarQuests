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
import org.bukkit.command.CommandSender;
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
    
    @Subcommand("save")
    @CommandPermission("starquests.command.admin.save")
    public void save(CommandSender sender) {
        plugin.getQuestManager().getStorageHandler().saveData();
    }
}