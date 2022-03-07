package com.starmediadev.plugins.starquests.cmds;

import com.starmediadev.plugins.starquests.StarQuests;
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
    public void save() {
        plugin.getQuestManager().getStorageHandler().saveData();
    }
    
    @Subcommand("reload")
    @CommandPermission("starquests.command.admin.reload")
    public void reload() {
        plugin.getQuestManager().getStorageHandler().reload();
    }
}