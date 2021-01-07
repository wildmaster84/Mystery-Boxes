package br.com.stenoxz.caixas.type;

import br.com.stenoxz.caixas.item.BoxItem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@Builder
public class BoxType {

    @Setter
    private String name, displayName;

    @Setter
    private ItemStack icon;

    @Setter
    private List<BoxItem> items;
}
