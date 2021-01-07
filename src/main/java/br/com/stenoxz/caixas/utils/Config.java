package br.com.stenoxz.caixas.utils;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;

public class Config {

    private File file;
    private FileConfiguration config;

    public Config(String pathname, Plugin plugin) {
        try {
            File pluginDir = new File(plugin.getDataFolder(), pathname + ".yml");
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdirs();
            }
            if (!pluginDir.exists()) {
                pluginDir.createNewFile();
                copyConfig(plugin.getResource(pathname + ".yml"), pluginDir);
            }
            file = pluginDir;
            config = YamlConfiguration.loadConfiguration(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public boolean contains(String c) {
        return config.contains(c);
    }

    public void set(String key, Object value) {
        config.set(key, value);
    }

    public Object get(String key) {
        return config.get(key);
    }

    public FileConfiguration reload() {
        return config = YamlConfiguration.loadConfiguration(file);
    }

    public ConfigurationSection getConfigurationSection(String key) {
        return config.getConfigurationSection(key);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyConfig(InputStream i, File config) {
        try {
            OutputStream out = new FileOutputStream(config);
            byte[] buf = new byte[710];
            int len;
            while ((len = i.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            i.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
