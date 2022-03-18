package com.starmediadev.plugins.starquests.objects.actions;

import com.starmediadev.plugins.starquests.objects.*;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.enchantment.EnchantItemEvent;

public class EnchantItemAction extends QuestAction<EnchantItemEvent> {
    
    private Enchantment enchantment;
    private Material material;
    
    /**
     * Constructs an EnchantAmountAction
     */
    protected EnchantItemAction() {
        super("enchantitem");
    }
    
    public EnchantItemAction(Enchantment enchantment, Material material) {
        this();
        this.enchantment = enchantment;
        this.material = material;
    }
    
    public EnchantItemAction(Enchantment enchantment) {
        this(enchantment, null);
    }
    
    public EnchantItemAction(Material material) {
        this(null, material);
    }
    
    @Override
    public void onAction(EnchantItemEvent object, Player player, Quest quest, QuestObjective questObjective) {
        if (enchantment != null && material == null) {
            if (object.getEnchantsToAdd().containsKey(enchantment)) {
                questObjective.complete(player.getUniqueId());
            }
        } else if (enchantment != null && material != null) {
            if (object.getItem().getType() == this.material) {
                if (object.getEnchantsToAdd().containsKey(enchantment)) {
                    questObjective.complete(player.getUniqueId());
                }
            }
        } else if (enchantment == null && material != null) {
            if (object.getItem().getType() == this.material) {
                questObjective.complete(player.getUniqueId());
            }
        }
    }
}