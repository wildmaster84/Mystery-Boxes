package br.com.stenoxz.caixas.factory;

import br.com.stenoxz.caixas.Main;
import br.com.stenoxz.caixas.box.Box;
import br.com.stenoxz.caixas.type.BoxType;
import org.bukkit.Bukkit;

public class BoxFactory {

    public Box newBox(BoxType type, String playerName){
        return Box.builder()
                .type(type)
                .plugin(Main.getInstance())
                .owner(Bukkit.getPlayer(playerName))
                .build();
    }
}
