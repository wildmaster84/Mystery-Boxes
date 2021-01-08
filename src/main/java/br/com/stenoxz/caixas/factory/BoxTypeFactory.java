package br.com.stenoxz.caixas.factory;

import br.com.stenoxz.caixas.Main;
import br.com.stenoxz.caixas.item.BoxItem;
import br.com.stenoxz.caixas.item.rarity.BoxItemRarity;
import br.com.stenoxz.caixas.type.BoxType;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BoxTypeFactory {

    private final FileConfiguration config;

    public BoxTypeFactory(Main plugin){
        this.config = plugin.getBoxesConfig().getConfig();
    }

    public BoxType newType(String name, String displayName, String icon, String items) {
        return BoxType.builder()
                .name(name)
                .displayName(ChatColor.translateAlternateColorCodes('&', displayName))
                .icon(getIcon(icon))
                .items(boxItems(items))
                .build();
    }

    private ItemStack getIcon(String icon){
        int id = Integer.parseInt(config.getString(icon + ".id"));

        ItemStack item = new ItemStack(id);
        ItemMeta meta = item.getItemMeta();

        if (Integer.parseInt(config.getString(icon + ".data")) != 0){
            item.setDurability(Short.parseShort(config.getString(icon + ".data")));
        }

        String name = ChatColor.translateAlternateColorCodes('&', config.getString(icon + ".name"));

        meta.setDisplayName(name);

        List<String> lore = new ArrayList<>();

        for (String s : config.getStringList(icon + ".lore")) {
            if (s.equalsIgnoreCase("")) continue;
            lore.add(ChatColor.translateAlternateColorCodes('&', s));
        }

        if (!lore.isEmpty()) {
            meta.setLore(lore);
        }

        List<String> enchantments = config.getStringList(icon + ".enchantments");

        if (!enchantments.isEmpty()){
            for (String enchants : enchantments) {
                if (!enchants.contains(";")){
                    continue;
                }

                String[] split = enchants.split(";");
                item.addUnsafeEnchantment(Enchantment.getById(Integer.parseInt(split[0])), Integer.parseInt(split[1]));
            }
        }

        item.setItemMeta(meta);

        return item;

        /*String[] split = icon.split(",");

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

        return item_add;*/
    }

    private List<BoxItem> boxItems(String items) {
        List<BoxItem> boxItems = new ArrayList<>();

        BoxItemFactory factory = new BoxItemFactory();

        config.getConfigurationSection(items).getKeys(false).forEach(string -> {
            int id = Integer.parseInt(config.getString(items + "." + string + ".id"));
            int amount = Integer.parseInt(config.getString(items + "." + string + ".amount"));

            ItemStack item = new ItemStack(id, amount);
            ItemMeta meta = item.getItemMeta();

            if (Integer.parseInt(config.getString(items + "." + string + ".data")) != 0){
                item.setDurability(Short.parseShort(config.getString(items + "." + string + ".data")));
            }

            String name = ChatColor.translateAlternateColorCodes('&', config.getString(items + "." + string + ".name"));

            if (!name.equalsIgnoreCase(""))
                meta.setDisplayName(name);

            List<String> lore = new ArrayList<>();

            for (String s : config.getStringList(items + "." + string + ".lore")) {
                if (s.equalsIgnoreCase("")) continue;
                lore.add(ChatColor.translateAlternateColorCodes('&', s));
            }

            if (!lore.isEmpty()) {
                meta.setLore(lore);
            }

            List<String> enchantments = config.getStringList(items + "." + string + ".enchantments");

            if (!enchantments.isEmpty()){
                for (String enchants : enchantments) {
                    if (!enchants.contains(";")){
                        continue;
                    }

                    String[] split = enchants.split(";");
                    meta.addEnchant(Enchantment.getById(Integer.parseInt(split[0])), Integer.parseInt(split[1]), true);
                }
            }

            item.setItemMeta(meta);

            BoxItemRarity rarity = Main.getInstance().getController().rarity(config.getString(items + "." + string + ".rarity"));

            String command = config.getString(items + "." + string + ".command");

            boxItems.add(factory.newBoxItem(item, command.equalsIgnoreCase("") ? null : command, rarity));
        });

        /*for (String spliter : items) {
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

                if (split[3].contains(" ")){
                    String[] enchantments = split[3].split(" ");
                    String[] levels = split[4].split(" ");

                    for (int i = 0; i < enchantments.length; i++){
                        item_add.addUnsafeEnchantment(Enchantment.getById(Integer.parseInt(enchantments[i])), Integer.parseInt(levels[i]));
                    }
                } else {
                    item_add.addUnsafeEnchantment(Enchantment.getById(Integer.parseInt(split[3])), Integer.parseInt(split[4]));
                }
            }

            item_add.setAmount(amount);

            BoxItemFactory itemFactory = new BoxItemFactory();

            BoxItem item = itemFactory.newBoxItem(item_add, rarity);

            boxItems.add(item);
        }*/

        return boxItems;
    }
}
