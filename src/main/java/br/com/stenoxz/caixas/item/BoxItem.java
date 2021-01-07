package br.com.stenoxz.caixas.item;

import br.com.stenoxz.caixas.item.rarity.BoxItemRarity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Getter
@Builder
public class BoxItem {

    @Setter
    private ItemStack item;

    @Setter
    private BoxItemRarity rarity;
}
