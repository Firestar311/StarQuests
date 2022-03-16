package com.starmediadev.plugins.starquests.objects.actions;

import com.starmediadev.plugins.starquests.objects.Quest;
import com.starmediadev.plugins.starquests.objects.QuestObjective;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.enchantment.EnchantItemEvent;

import java.util.ArrayList;
import java.util.List;

public class EnchantAmountAction extends QuestAction<EnchantItemEvent> {
    
    private List<Enchantment> enchantments = new ArrayList<>();
    private List<Material> materials = new ArrayList<>();
    
    private int enchantmentAmount = 0, materialAmount = 0;
    
    /**
     * Constructs an EnchantAmountAction
     */
    public EnchantAmountAction() {
        super("enchantamout");
    }
    
    @Override
    public void onAction(EnchantItemEvent object, Player player, Quest quest, QuestObjective questObjective) {
        
    }
}
