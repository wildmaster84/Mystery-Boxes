package br.com.stenoxz.caixas.box;

import br.com.stenoxz.caixas.Main;
import br.com.stenoxz.caixas.item.BoxItem;
import br.com.stenoxz.caixas.type.BoxType;
import br.com.stenoxz.caixas.utils.ItemBuilder;
import br.com.stenoxz.caixas.utils.TextUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;

@Getter
@Builder
public class Box {

    @Setter
    private BoxType type;

    private BukkitTask task;

    public ItemStack getItem(){
        return type.getIcon();
    }

    public void openBox(Player player){
        Inventory inventory = Bukkit.createInventory(null, 3*9, TextUtils.format(Main.inventoryTitle, type));

        fillGlass(inventory);

        player.openInventory(inventory);

        task = new BukkitRunnable(){
            int i = 0;

            @Override
            public void run() {
                i++;

                if (player.getOpenInventory().getTopInventory().equals(inventory)) {
                    if (i == 1){
                        itemRotate(inventory);
                    }

                    player.playSound(player.getLocation(), Sound.CLICK, 1F, 1F);
                }

                if (i == 40){
                    win(player, inventory.getItem(13));
                    cancel();
                    task = null;
                }
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0L, 5L);
    }

    private void win(Player player, ItemStack stack){
        new BukkitRunnable(){
            @Override
            public void run() {
                if (player.getInventory().firstEmpty() == -1){
                    player.getWorld().dropItem(player.getLocation(), stack);
                } else {
                    player.getInventory().addItem(stack);
                }
                player.closeInventory();

                BoxItem item = null;

                for (BoxItem i : type.getItems()){
                    if (i.getItem().equals(stack)) {
                        item = i;
                        break;
                    }
                }

                if (item == null || !item.getItem().hasItemMeta() || !item.getItem().getItemMeta().hasDisplayName() || item.getRarity().getPercentage() > 10) return;

                TextComponent message = new TextComponent("");

                BaseComponent baseComponent = new TextComponent(Main.broadcastRare(player.getName(), item.getRarity(), type));
                baseComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[] { new TextComponent(stack.getItemMeta().getDisplayName()) }));

                message.addExtra(baseComponent);

                Bukkit.getOnlinePlayers().forEach(player -> player.spigot().sendMessage(message));

                player.getWorld().strikeLightningEffect(player.getLocation());
            }
        }.runTaskLaterAsynchronously(Main.getInstance(), 40L);
    }

    private void itemRotate(Inventory inv){
        new BukkitRunnable(){
            final ItemStack item = randomItem();
            int i = 9;

            @Override
            public void run() {
                if (task == null){
                    cancel();
                    return;
                }
                i++;
                if (i == 17){
                    cancel();
                    return;
                }
                inv.setItem(i, item);

                if (i == 11)
                    itemRotate(inv);
            }
        }.runTaskTimer(Main.getInstance(), 0L, 5L);
    }

    private ItemStack randomItem(){
        BoxItem item = type.getItems().get(new Random().nextInt(type.getItems().size()));

        if (new Random().nextInt(100) <= item.getRarity().getPercentage()){
            return item.getItem();
        } else {
            return randomItem();
        }
    }

    private void fillGlass(Inventory inventory){
        inventory.setItem(0, new ItemBuilder().setMaterial(Material.STAINED_GLASS_PANE).setDurability(14).getStack());
        inventory.setItem(9, new ItemBuilder().setMaterial(Material.STAINED_GLASS_PANE).setDurability(14).getStack());
        inventory.setItem(18, new ItemBuilder().setMaterial(Material.STAINED_GLASS_PANE).setDurability(14).getStack());

        inventory.setItem(1, new ItemBuilder().setMaterial(Material.STAINED_GLASS_PANE).setDurability(1).getStack());
        inventory.setItem(19, new ItemBuilder().setMaterial(Material.STAINED_GLASS_PANE).setDurability(1).getStack());

        inventory.setItem(2, new ItemBuilder().setMaterial(Material.STAINED_GLASS_PANE).setDurability(4).getStack());
        inventory.setItem(20, new ItemBuilder().setMaterial(Material.STAINED_GLASS_PANE).setDurability(4).getStack());

        inventory.setItem(3, new ItemBuilder().setMaterial(Material.STAINED_GLASS_PANE).setDurability(5).getStack());
        inventory.setItem(21, new ItemBuilder().setMaterial(Material.STAINED_GLASS_PANE).setDurability(5).getStack());

        inventory.setItem(4, new ItemBuilder().setMaterial(Material.STAINED_GLASS_PANE).setDurability(15).getStack());
        inventory.setItem(22, new ItemBuilder().setMaterial(Material.STAINED_GLASS_PANE).setDurability(15).getStack());

        inventory.setItem(5, new ItemBuilder().setMaterial(Material.STAINED_GLASS_PANE).setDurability(11).getStack());
        inventory.setItem(23, new ItemBuilder().setMaterial(Material.STAINED_GLASS_PANE).setDurability(11).getStack());

        inventory.setItem(6, new ItemBuilder().setMaterial(Material.STAINED_GLASS_PANE).setDurability(10).getStack());
        inventory.setItem(24, new ItemBuilder().setMaterial(Material.STAINED_GLASS_PANE).setDurability(10).getStack());

        inventory.setItem(7, new ItemBuilder().setMaterial(Material.STAINED_GLASS_PANE).setDurability(6).getStack());
        inventory.setItem(25, new ItemBuilder().setMaterial(Material.STAINED_GLASS_PANE).setDurability(6).getStack());

        inventory.setItem(8, new ItemBuilder().setMaterial(Material.STAINED_GLASS_PANE).setDurability(9).getStack());
        inventory.setItem(17, new ItemBuilder().setMaterial(Material.STAINED_GLASS_PANE).setDurability(9).getStack());
        inventory.setItem(26, new ItemBuilder().setMaterial(Material.STAINED_GLASS_PANE).setDurability(9).getStack());
    }
}
