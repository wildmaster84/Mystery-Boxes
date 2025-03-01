package br.com.stenoxz.caixas;

import br.com.stenoxz.caixas.command.GiveBoxCommand;
import br.com.stenoxz.caixas.controller.BoxController;
import br.com.stenoxz.caixas.event.TimeSecondEvent;
import br.com.stenoxz.caixas.factory.BoxFactory;
import br.com.stenoxz.caixas.item.rarity.BoxItemRarity;
import br.com.stenoxz.caixas.listeners.BoxListeners;
import br.com.stenoxz.caixas.type.BoxType;
import br.com.stenoxz.caixas.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_21_R3.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
   public static Main instance;
   private BoxController controller;
   private BoxFactory factory;
   private Config boxesConfig;
   private Config raritiesConfig;
   public static String inventoryTitle;
   private static String broadcastRareMessage;

   public static Main getInstance() {
      return instance;
   }

   public BoxController getController() {
      return this.controller;
   }

   public BoxFactory getFactory() {
      return this.factory;
   }

   public Config getBoxesConfig() {
      return this.boxesConfig;
   }

   public Config getRaritiesConfig() {
      return this.raritiesConfig;
   }

   public void onLoad() {
      this.controller = new BoxController(this);
      this.factory = new BoxFactory();
   }

   public void onEnable() {
      instance = this;
      this.saveDefaultConfig();
      inventoryTitle = this.getConfig().getString("inventoryTitle");
      broadcastRareMessage = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("broadcastRare"));
      this.boxesConfig = new Config("caixas", this);
      this.raritiesConfig = new Config("raridades", this);
      this.controller.loadRarities();
      this.controller.loadTypes();
      Bukkit.getPluginManager().registerEvents(new BoxListeners(this), this);
      ((CraftServer)Bukkit.getServer()).getCommandMap().register("givebox", new GiveBoxCommand(this));
      Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> new TimeSecondEvent().call(), 0L, 5L);
   }

   public static String broadcastRare(String playerName, BoxItemRarity rarity, BoxType type) {
      return broadcastRareMessage.replaceAll("%player%", playerName)
         .replaceAll("%rarity%", rarity.getDisplayName())
         .replaceAll("%type%", type.getDisplayName());
   }
}