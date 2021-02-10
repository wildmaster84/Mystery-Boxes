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
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main instance;

    public static Main getInstance() {
        return instance;
    }

    private BoxController controller;

    public BoxController getController() {
        return controller;
    }

    private BoxFactory factory;

    public BoxFactory getFactory() {
        return factory;
    }

    private Config boxesConfig;

    public Config getBoxesConfig() {
        return boxesConfig;
    }

    private Config raritiesConfig;

    public Config getRaritiesConfig() {
        return raritiesConfig;
    }

    public static String inventoryTitle;

    private static String broadcastRareMessage;

    @Override
    public void onLoad() {
        controller = new BoxController(this);
        factory = new BoxFactory();
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        inventoryTitle = getConfig().getString("inventoryTitle");
        broadcastRareMessage = ChatColor.translateAlternateColorCodes('&', getConfig().getString("broadcastRare"));

        boxesConfig = new Config("caixas", this);

        raritiesConfig = new Config("raridades", this);

        controller.loadRarities();
        controller.loadTypes();

        Bukkit.getPluginManager().registerEvents(new BoxListeners(this), this);

        ((CraftServer)Bukkit.getServer()).getCommandMap().register("givebox", new GiveBoxCommand(this));

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, () -> {
            new TimeSecondEvent().call();
        }, 0L, 5L);
    }

    public static String broadcastRare(String playerName, BoxItemRarity rarity, BoxType type){
        return broadcastRareMessage.replaceAll("%player%", playerName).replaceAll("%rarity%", rarity.getDisplayName()).replaceAll("%type%", type.getDisplayName());
    }
}
