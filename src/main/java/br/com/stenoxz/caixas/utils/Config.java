package br.com.stenoxz.caixas.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

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
            this.copyConfig(plugin.getResource(pathname + ".yml"), pluginDir);
         }

         this.file = pluginDir;
         this.config = YamlConfiguration.loadConfiguration(this.file);
      } catch (IOException var4) {
         var4.printStackTrace();
      }
   }

   public FileConfiguration getConfig() {
      return this.config;
   }

   public boolean contains(String c) {
      return this.config.contains(c);
   }

   public void set(String key, Object value) {
      this.config.set(key, value);
   }

   public Object get(String key) {
      return this.config.get(key);
   }

   public FileConfiguration reload() {
      return this.config = YamlConfiguration.loadConfiguration(this.file);
   }

   public ConfigurationSection getConfigurationSection(String key) {
      return this.config.getConfigurationSection(key);
   }

   public void save() {
      try {
         this.config.save(this.file);
      } catch (IOException var2) {
         var2.printStackTrace();
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
      } catch (Exception var6) {
         var6.printStackTrace();
      }
   }
}