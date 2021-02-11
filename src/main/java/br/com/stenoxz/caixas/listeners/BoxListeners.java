package br.com.stenoxz.caixas.listeners;

import br.com.stenoxz.caixas.Main;
import br.com.stenoxz.caixas.box.Box;
import br.com.stenoxz.caixas.event.TimeSecondEvent;
import br.com.stenoxz.caixas.item.BoxItem;
import br.com.stenoxz.caixas.type.BoxType;
import br.com.stenoxz.caixas.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BoxListeners implements Listener {

    private final Main plugin;

    public BoxListeners(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();

        if (event.getItem() != null &&
                event.getItem().hasItemMeta() &&
                event.getItem().getItemMeta().hasDisplayName()){
            ItemStack stack = event.getItem();

            for (BoxType type : plugin.getController().getTypes()) {
                if (stack.getItemMeta().equals(type.getIcon().getItemMeta()) &&
                        stack.getItemMeta().getLore().equals(type.getIcon().getItemMeta().getLore())){
                    event.setCancelled(true);

                    if (event.getAction().toString().contains("RIGHT")) {
                        if (stack.getAmount() == 1){
                            player.setItemInHand(null);
                        } else {
                            stack.setAmount(stack.getAmount() - 1);
                        }

                        Box box = plugin.getFactory().newBox(type, player.getName());
                        box.openBox();
                    } else {
                        Inventory inventory = Bukkit.createInventory(null, 54, "Itens da Caixa " + type.getName());

                        for (BoxItem item : type.getItems()){
                            inventory.addItem(item.getItem());
                        }

                        player.openInventory(inventory);
                    }
                    break;
                }
            }
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

    @EventHandler
    public void onTimeSecondEvent(TimeSecondEvent event){
        plugin.getController().getBoxes().forEach(Box::itemRotate);
    }
}
