package br.com.stenoxz.caixas.box;

import br.com.stenoxz.caixas.Main;
import br.com.stenoxz.caixas.item.BoxItem;
import br.com.stenoxz.caixas.type.BoxType;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BoxBuilder {
   private BoxType type;
   private Main plugin;
   private Player owner;
   private int i;
   private int rotation;
   private Map<Integer, BoxItem> items;
   private Inventory inv;

   BoxBuilder() {
   }

   public BoxBuilder type(BoxType type) {
      this.type = type;
      return this;
   }

   public BoxBuilder plugin(Main plugin) {
      this.plugin = plugin;
      return this;
   }

   public BoxBuilder owner(Player owner) {
      this.owner = owner;
      return this;
   }

   public BoxBuilder i(int i) {
      this.i = i;
      return this;
   }

   public BoxBuilder rotation(int rotation) {
      this.rotation = rotation;
      return this;
   }

   public BoxBuilder items(Map<Integer, BoxItem> items) {
      this.items = items;
      return this;
   }

   public BoxBuilder inv(Inventory inv) {
      this.inv = inv;
      return this;
   }

   public Box build() {
      return new Box(this.type, this.plugin, this.owner, this.i, this.rotation, this.items, this.inv);
   }

   public String toString() {
      return "Box.BoxBuilder(type="
         + this.type
         + ", plugin="
         + this.plugin
         + ", owner="
         + this.owner
         + ", i="
         + this.i
         + ", rotation="
         + this.rotation
         + ", items="
         + this.items
         + ", inv="
         + this.inv
         + ")";
   }
}