package br.com.stenoxz.caixas.controller;

import br.com.stenoxz.caixas.Main;
import br.com.stenoxz.caixas.box.Box;
import br.com.stenoxz.caixas.factory.BoxItemRarityFactory;
import br.com.stenoxz.caixas.factory.BoxTypeFactory;
import br.com.stenoxz.caixas.item.rarity.BoxItemRarity;
import br.com.stenoxz.caixas.type.BoxType;
import br.com.stenoxz.caixas.utils.Config;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class BoxController {

    private final Main plugin;

    private Set<BoxItemRarity> rarities;

    private Set<BoxType> types;

    {
        rarities = new HashSet<>();
        types = new HashSet<>();
    }

    public void loadRarities() {
        BoxItemRarityFactory factory = new BoxItemRarityFactory();

        Config raritiesConfig = plugin.getRaritiesConfig();

        for (String string : raritiesConfig.getConfig().getKeys(false)) {
            rarities.add(factory.newRarity((String) raritiesConfig.get(string + ".name"),
                    (String) raritiesConfig.get(string + ".displayName"),
                    (int) raritiesConfig.get(string + ".percentage")));
        }
    }

    public void loadTypes() {
        BoxTypeFactory factory = new BoxTypeFactory(plugin);

        FileConfiguration config = plugin.getBoxesConfig().getConfig();

        for (String string : config.getKeys(false)) {
            types.add(factory.newType(config.getString(string + ".tipo.name"),
                    config.getString(string + ".tipo.displayName"),
                    string + ".tipo.icon",
                    string + ".tipo.items"));
        }
    }

    public BoxItemRarity rarity(String name) {
        return rarities.stream().filter(rarity -> rarity.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public BoxType type(String name) {
        return types.stream().filter(type -> type.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void giveBox(Player player, BoxType type) {
        Box box = plugin.getFactory().newBox(type);

        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItem(player.getLocation(), box.getItem());
        } else {
            player.getInventory().addItem(box.getItem());
        }
    }
}
