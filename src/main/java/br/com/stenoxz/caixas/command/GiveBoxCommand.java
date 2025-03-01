package br.com.stenoxz.caixas.command;

import br.com.stenoxz.caixas.Main;
import br.com.stenoxz.caixas.type.BoxType;
import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveBoxCommand extends Command {
   private final Main plugin;

   public GiveBoxCommand(Main plugin) {
      super("givebox", "", "", Arrays.asList("givecaixa", "darcaixa"));
      this.plugin = plugin;
   }

   public boolean execute(CommandSender sender, String lb, String[] args) {
      if (!sender.hasPermission("scaixas.admin")) {
         return false;
      } else if (args.length != 2) {
         sender.sendMessage("§cUsage: /" + lb + " [player/all] [caixa]");
         return false;
      } else {
         boolean all = args[0].equalsIgnoreCase("all");
         BoxType type = this.plugin.getController().type(args[1]);
         if (type == null) {
            sender.sendMessage("§cCaixas disponíveis:");

            for (BoxType t : this.plugin.getController().getTypes()) {
               sender.sendMessage("§f" + t.getName());
            }

            return false;
         } else {
            if (!all) {
               Player player = Bukkit.getPlayer(args[0]);
               if (player == null) {
                  sender.sendMessage("§cPlayer inválido.");
                  return false;
               }

               this.plugin.getController().giveBox(player, type);
            } else {
               for (Player player : Bukkit.getOnlinePlayers()) {
                  this.plugin.getController().giveBox(player, type);
               }
            }

            return false;
         }
      }
   }
}