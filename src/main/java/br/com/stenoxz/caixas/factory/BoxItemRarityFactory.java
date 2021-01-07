package br.com.stenoxz.caixas.factory;

import br.com.stenoxz.caixas.item.rarity.BoxItemRarity;
import org.bukkit.ChatColor;

public class BoxItemRarityFactory {

    public BoxItemRarity newRarity(String name, String displayName, int percentage){
        return BoxItemRarity.builder()
                .name(name)
                .displayName(ChatColor.translateAlternateColorCodes('&', displayName))
                .percentage(percentage)
                .build();
    }
}
