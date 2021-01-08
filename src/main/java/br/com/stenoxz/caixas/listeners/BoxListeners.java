package br.com.stenoxz.caixas.listeners;

import br.com.stenoxz.caixas.Main;
import br.com.stenoxz.caixas.box.Box;
import br.com.stenoxz.caixas.item.BoxItem;
import br.com.stenoxz.caixas.utils.TextUtils;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class BoxListeners implements Listener {

    private final Main plugin;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();

        if (event.getItem() != null && event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasDisplayName()){
            ItemStack stack = event.getItem();

            plugin.getController().getTypes().forEach(type -> {
                if (stack.equals(type.getIcon())){
                    event.setCancelled(true);

                    if (event.getAction().toString().contains("RIGHT")) {
                        Box box = plugin.getFactory().newBox(type);
                        box.openBox(player);

                        player.getInventory().removeItem(stack);
                    } else {
                        Inventory inventory = Bukkit.createInventory(null, 54, "Itens da Caixa " + type.getName());

                        for (BoxItem item : type.getItems()){
                            inventory.addItem(item.getItem());
                        }

                        player.openInventory(inventory);

                    }
                }
            });
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if (event.getClickedInventory() == null || event.getClickedInventory().getTitle() == null) return;

        plugin.getController().getTypes().forEach(type -> {
            if (TextUtils.format(event.getClickedInventory().getTitle(), type).equals(TextUtils.format(Main.inventoryTitle, type)) || event.getClickedInventory().getTitle().startsWith("Itens da Caixa")){
                event.setCancelled(true);
            }
        });

        if (event.getInventory().getTitle().replaceAll("%type%", "").equals(ChatColor.translateAlternateColorCodes('&', Main.inventoryTitle).replaceAll("%type%", ""))){
            event.setCancelled(true);
        }
    }
}
