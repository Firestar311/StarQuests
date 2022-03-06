package com.starmediadev.plugins.starquests.objects.actions;

import com.starmediadev.plugins.starquests.StarQuests;
import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import com.starmediadev.plugins.starquests.objects.runnables.NPCDialogRunnable;
import com.starmediadev.utils.collection.IncrementalMap;
import net.citizensnpcs.api.event.NPCClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;

public class NPCClickAction extends QuestAction<NPCClickEvent> {
    
    private IncrementalMap<String> dialogText;
    private long delayInTicks;
    
    public NPCClickAction(IncrementalMap<String> dialogText, long delayInTicks) {
        super("npcclick");
        this.dialogText = dialogText;
        this.delayInTicks = delayInTicks;
    }
    
    @Override
    public void onAction(NPCClickEvent event, Quest quest, QuestObjective questObjective) {
        Player player = event.getClicker();
        NPC npc = event.getNPC();
        NPCDialogRunnable runnable = new NPCDialogRunnable(dialogText, player, npc);
        runnable.runTaskTimer(StarQuests.getPlugin(StarQuests.class), 1L, delayInTicks);
    }
}
