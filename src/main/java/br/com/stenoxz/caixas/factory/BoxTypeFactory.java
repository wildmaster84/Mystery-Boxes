package br.com.stenoxz.caixas.factory;

import br.com.stenoxz.caixas.Main;
import br.com.stenoxz.caixas.item.BoxItem;
import br.com.stenoxz.caixas.item.rarity.BoxItemRarity;
import br.com.stenoxz.caixas.type.BoxType;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BoxTypeFactory {

    public BoxType newType(String name, String displayName, String icon, List<String> items) {
        return BoxType.builder()
                .name(name)
                .displayName(ChatColor.translateAlternateColorCodes('&', displayName))
                .icon(getIcon(icon, displayName))
                .items(boxItems(items))
                .build();
    }

    private ItemStack getIcon(String icon, String displayName){
        String[] split = icon.split(",");

        String itemId = split[0];
        short durability = 9999;

        int id;

        if (icon.contains(";")) {
            String[] Split = itemId.split(";");
            id = Integer.parseInt(Split[0]);
            durability = Short.parseShort(Split[1]);
        } else {
            id = Integer.parseInt(itemId);
        }

        ItemStack item_add = new ItemStack(id, 1);

        if (durability != 9999) {
            item_add.setDurability(durability);
        }

        if (split.length == 3) {
            item_add.addUnsafeEnchantment(Enchantment.getById(Integer.parseInt(split[1])), Integer.parseInt(split[2]));
        }

        ItemMeta meta = item_add.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        item_add.setItemMeta(meta);

        return item_add;
    }

    private List<BoxItem> boxItems(List<String> items) {
        List<BoxItem> boxItems = new ArrayList<>();

        for (String spliter : items) {
            String[] split = spliter.split(",");
            String itemId = split[0];
            int amount = Integer.parseInt(split[1]);

            BoxItemRarity rarity = Main.getInstance().getController().rarity(split[2]);

            short durability = 9999;
            int id;

            if (spliter.contains(";")) {
                String[] Split = itemId.split(";");
                id = Integer.parseInt(Split[0]);
                durability = Short.parseShort(Split[1]);
            } else {
                id = Integer.parseInt(itemId);
            }
            ItemStack item_add = new ItemStack(id, 1);

            if (durability != 9999) {
                item_add.setDurability(durability);
            }

            if (split.length == 5) {
                item_add.addUnsafeEnchantment(Enchantment.getById(Integer.parseInt(split[3])), Integer.parseInt(split[4]));
            }

            item_add.setAmount(amount);

            BoxItemFactory itemFactory = new BoxItemFactory();

            BoxItem item = itemFactory.newBoxItem(item_add, rarity);

            boxItems.add(item);
        }

        return boxItems;
    }
}
