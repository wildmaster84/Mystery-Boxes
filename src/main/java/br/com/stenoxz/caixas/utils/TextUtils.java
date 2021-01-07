package br.com.stenoxz.caixas.utils;

import br.com.stenoxz.caixas.type.BoxType;
import org.bukkit.ChatColor;

public class TextUtils {

    public static String format(String string, BoxType type){
        return ChatColor.translateAlternateColorCodes('&', string.replaceAll("%type%", type.getName()));
    }
}
