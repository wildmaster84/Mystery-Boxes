package br.com.stenoxz.caixas.factory;

import br.com.stenoxz.caixas.item.BoxItem;
import br.com.stenoxz.caixas.item.rarity.BoxItemRarity;
import org.bukkit.inventory.ItemStack;

public class BoxItemFactory {

    public BoxItem newBoxItem(ItemStack item, String command, BoxItemRarity rarity){
        return BoxItem.builder()
                .item(item)
                .command(command)
                .rarity(rarity)
                .build();
    }
}
