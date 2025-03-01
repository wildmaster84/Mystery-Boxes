package br.com.stenoxz.caixas.type;

import br.com.stenoxz.caixas.item.BoxItem;

import java.util.List;
import org.bukkit.inventory.ItemStack;

public class BoxType {
   private String name;
   private String displayName;
   private ItemStack icon;
   private List<BoxItem> items;

   BoxType(String name, String displayName, ItemStack icon, List<BoxItem> items) {
      this.name = name;
      this.displayName = displayName;
      this.icon = icon;
      this.items = items;
   }

   public static BoxTypeBuilder builder() {
      return new BoxTypeBuilder();
   }

   public String getName() {
      return this.name;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public ItemStack getIcon() {
      return this.icon;
   }

   public List<BoxItem> getItems() {
      return this.items;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setDisplayName(String displayName) {
      this.displayName = displayName;
   }

   public void setIcon(ItemStack icon) {
      this.icon = icon;
   }

   public void setItems(List<BoxItem> items) {
      this.items = items;
   }
}