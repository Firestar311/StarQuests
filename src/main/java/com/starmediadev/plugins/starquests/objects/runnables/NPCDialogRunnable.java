package com.starmediadev.plugins.starquests.objects.runnables;

import com.starmediadev.plugins.starmcutils.util.MCUtils;
import com.starmediadev.plugins.starquests.StarQuests;
import com.starmediadev.utils.collection.IncrementalMap;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class NPCDialogRunnable extends BukkitRunnable {
    
    private IncrementalMap<String> dialogText;
    private Player player;
    private NPC npc;
    
    private StarQuests plugin = StarQuests.getPlugin(StarQuests.class);
    
    private int index = 0;
    
    public NPCDialogRunnable(IncrementalMap<String> dialogText, Player player, NPC npc) {
        this.dialogText = dialogText;
        this.player = player;
        this.npc = npc;
    }
    
    @Override
    public void run() {
        String dialogText = this.dialogText.get(index);
        String message = plugin.getConfig().getString("dialog.format");
        message = message.replace("{npcname}", npc.getName()).replace("{npctext}", dialogText);
        player.sendMessage(MCUtils.color(message));
        index++;
        if (index == this.dialogText.size()) {
            cancel();
        }
    }
}
