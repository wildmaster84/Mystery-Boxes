package br.com.stenoxz.caixas.utils;

import org.bukkit.ChatColor;

import br.com.stenoxz.caixas.type.BoxType;

public class TextUtils {

    public static String format(String string, BoxType type){
        return ChatColor.translateAlternateColorCodes('&', string.replaceAll("%type%", type.getName()));
    }
}
