package br.com.stenoxz.caixas.item;

import br.com.stenoxz.caixas.item.rarity.BoxItemRarity;
import org.bukkit.inventory.ItemStack;

public class BoxItem {
   private ItemStack item;
   private String command;
   private BoxItemRarity rarity;

   BoxItem(ItemStack item, String command, BoxItemRarity rarity) {
      this.item = item;
      this.command = command;
      this.rarity = rarity;
   }

   public static BoxItemBuilder builder() {
      return new BoxItemBuilder();
   }

   public ItemStack getItem() {
      return this.item;
   }

   public String getCommand() {
      return this.command;
   }

   public BoxItemRarity getRarity() {
      return this.rarity;
   }

   public void setItem(ItemStack item) {
      this.item = item;
   }

   public void setCommand(String command) {
      this.command = command;
   }

   public void setRarity(BoxItemRarity rarity) {
      this.rarity = rarity;
   }
}