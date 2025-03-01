package br.com.stenoxz.caixas.item;

import br.com.stenoxz.caixas.item.rarity.BoxItemRarity;
import org.bukkit.inventory.ItemStack;

public class BoxItemBuilder {
   private ItemStack item;
   private String command;
   private BoxItemRarity rarity;

   BoxItemBuilder() {
   }

   public BoxItemBuilder item(ItemStack item) {
      this.item = item;
      return this;
   }

   public BoxItemBuilder command(String command) {
      this.command = command;
      return this;
   }

   public BoxItemBuilder rarity(BoxItemRarity rarity) {
      this.rarity = rarity;
      return this;
   }

   public BoxItem build() {
      return new BoxItem(this.item, this.command, this.rarity);
   }

   public String toString() {
      return "BoxItem.BoxItemBuilder(item=" + this.item + ", command=" + this.command + ", rarity=" + this.rarity + ")";
   }
}