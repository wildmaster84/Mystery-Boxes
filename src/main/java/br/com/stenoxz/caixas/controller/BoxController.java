package br.com.stenoxz.caixas.controller;

import br.com.stenoxz.caixas.Main;
import br.com.stenoxz.caixas.box.Box;
import br.com.stenoxz.caixas.factory.BoxItemRarityFactory;
import br.com.stenoxz.caixas.factory.BoxTypeFactory;
import br.com.stenoxz.caixas.item.rarity.BoxItemRarity;
import br.com.stenoxz.caixas.type.BoxType;
import br.com.stenoxz.caixas.utils.Config;
import com.google.common.collect.Sets;
import java.util.Set;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BoxController {
   private final Main plugin;
   private Set<BoxItemRarity> rarities = Sets.newConcurrentHashSet();
   private Set<BoxType> types = Sets.newConcurrentHashSet();
   private Set<Box> boxes = Sets.newConcurrentHashSet();

   public void loadRarities() {
      BoxItemRarityFactory factory = new BoxItemRarityFactory();
      Config raritiesConfig = this.plugin.getRaritiesConfig();

      for (String string : raritiesConfig.getConfig().getKeys(false)) {
         this.rarities
            .add(
               factory.newRarity(
                  (String)raritiesConfig.get(string + ".name"),
                  (String)raritiesConfig.get(string + ".displayName"),
                  (Integer)raritiesConfig.get(string + ".percentage")
               )
            );
      }
   }

   public void loadTypes() {
      BoxTypeFactory factory = new BoxTypeFactory(this.plugin);
      FileConfiguration config = this.plugin.getBoxesConfig().getConfig();

      for (String string : config.getKeys(false)) {
         this.types
            .add(
               factory.newType(
                  config.getString(string + ".tipo.name"), config.getString(string + ".tipo.displayName"), string + ".tipo.icon", string + ".tipo.items"
               )
            );
      }
   }

   public BoxItemRarity rarity(String name) {
      return (BoxItemRarity)this.rarities.stream().filter(rarity -> rarity.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
   }

   public BoxType type(String name) {
      return (BoxType)this.types.stream().filter(type -> type.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
   }

   public void create(Box box) {
      this.boxes.add(box);
   }

   public void remove(Box box) {
      this.boxes.remove(box);
   }

   public void giveBox(Player player, BoxType type) {
      Box box = this.plugin.getFactory().newBox(type, player.getName());
      if (player.getInventory().firstEmpty() == -1) {
         player.getWorld().dropItem(player.getLocation(), box.getItem());
      } else {
         player.getInventory().addItem(new ItemStack[]{box.getItem()});
      }
   }

   public Main getPlugin() {
      return this.plugin;
   }

   public Set<BoxItemRarity> getRarities() {
      return this.rarities;
   }

   public Set<BoxType> getTypes() {
      return this.types;
   }

   public Set<Box> getBoxes() {
      return this.boxes;
   }

   public BoxController(Main plugin) {
      this.plugin = plugin;
   }
}