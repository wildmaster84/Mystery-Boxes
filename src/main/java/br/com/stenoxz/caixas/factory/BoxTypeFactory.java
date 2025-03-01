package br.com.stenoxz.caixas.factory;

import br.com.stenoxz.caixas.Main;
import br.com.stenoxz.caixas.item.BoxItem;
import br.com.stenoxz.caixas.item.rarity.BoxItemRarity;
import br.com.stenoxz.caixas.type.BoxType;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BoxTypeFactory {
   private final FileConfiguration config;

   public BoxTypeFactory(Main plugin) {
      this.config = plugin.getBoxesConfig().getConfig();
   }

   public BoxType newType(String name, String displayName, String icon, String items) {
      return BoxType.builder()
         .name(name)
         .displayName(ChatColor.translateAlternateColorCodes('&', displayName))
         .icon(this.getIcon(icon))
         .items(this.boxItems(items))
         .build();
   }

   private ItemStack getIcon(String icon) {
      String id = this.config.getString(icon + ".id");
      ItemStack item = new ItemStack(Material.valueOf(id.toUpperCase()));
      ItemMeta meta = item.getItemMeta();
      if (Integer.parseInt(this.config.getString(icon + ".data")) != 0) {
         item.setDurability(Short.parseShort(this.config.getString(icon + ".data")));
      }

      String name = ChatColor.translateAlternateColorCodes('&', this.config.getString(icon + ".name"));
      meta.setDisplayName(name);
      List<String> lore = new ArrayList();

      for (String s : this.config.getStringList(icon + ".lore")) {
         if (!s.equalsIgnoreCase("")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', s));
         }
      }

      if (!lore.isEmpty()) {
         meta.setLore(lore);
      }

      List<String> enchantments = this.config.getStringList(icon + ".enchantments");
      if (!enchantments.isEmpty()) {
         for (String enchants : enchantments) {
            if (enchants.contains(";")) {
               String[] split = enchants.split(";");
               item.addUnsafeEnchantment(Enchantment.getByName(split[0].toUpperCase()), Integer.parseInt(split[1]));
            }
         }
      }

      item.setItemMeta(meta);
      return item;
   }

   private List<BoxItem> boxItems(String items) {
      List<BoxItem> boxItems = new ArrayList();
      BoxItemFactory factory = new BoxItemFactory();
      this.config.getConfigurationSection(items).getKeys(false).forEach(string -> {
         String id = this.config.getString(items + "." + string + ".id");
         int amount = Integer.parseInt(this.config.getString(items + "." + string + ".amount"));
         ItemStack item = new ItemStack(Material.valueOf(id.toUpperCase()), amount);
         ItemMeta meta = item.getItemMeta();
         if (Integer.parseInt(this.config.getString(items + "." + string + ".data")) != 0) {
            item.setDurability(Short.parseShort(this.config.getString(items + "." + string + ".data")));
         }

         String name = ChatColor.translateAlternateColorCodes('&', this.config.getString(items + "." + string + ".name"));
         if (!name.equalsIgnoreCase("")) {
            meta.setDisplayName(name);
         }

         List<String> lore = new ArrayList();

         for (String s : this.config.getStringList(items + "." + string + ".lore")) {
            if (!s.equalsIgnoreCase("")) {
               lore.add(ChatColor.translateAlternateColorCodes('&', s));
            }
         }

         if (!lore.isEmpty()) {
            meta.setLore(lore);
         }

         List<String> enchantments = this.config.getStringList(items + "." + string + ".enchantments");
         if (!enchantments.isEmpty()) {
            for (String enchants : enchantments) {
               if (enchants.contains(";")) {
                  String[] split = enchants.split(";");
                  meta.addEnchant(Enchantment.getByName(split[0].toUpperCase()), Integer.parseInt(split[1]), true);
               }
            }
         }

         item.setItemMeta(meta);
         BoxItemRarity rarity = Main.getInstance().getController().rarity(this.config.getString(items + "." + string + ".rarity"));
         String command = this.config.getString(items + "." + string + ".command");
         boxItems.add(factory.newBoxItem(item, command.equalsIgnoreCase("") ? null : command, rarity));
      });
      return boxItems;
   }
}