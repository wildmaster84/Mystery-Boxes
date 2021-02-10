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
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

@Getter
@Builder
public class Box implements Listener {

    @Setter
    private BoxType type;

    private final Main plugin;

    private final Player owner;

    private int i, rotation;

    private Map<Integer, BoxItem> items;

    private Inventory inv;

    public ItemStack getItem() {
        return type.getIcon();
    }

    public void openBox() {
        if (owner == null) return;

        inv = Bukkit.createInventory(owner, 3 * 9, TextUtils.format(Main.inventoryTitle, type));

        fillGlass(inv);

        owner.openInventory(inv);

        plugin.getController().create(this);

        //itemRotate(inventory);
    }

    public void win(ItemStack stack) {
        if (owner == null) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                BoxItem item = null;

                for (BoxItem i : type.getItems()) {
                    if (i.getItem().equals(stack)) {
                        item = i;
                        break;
                    }
                }

                owner.closeInventory();

                if (item == null) return;

                if (item.getCommand() == null || item.getCommand().equalsIgnoreCase("")) {
                    if (owner.getInventory().firstEmpty() == -1) {
                        owner.getWorld().dropItem(owner.getLocation(), stack);
                    } else {
                        owner.getInventory().addItem(stack);
                    }
                } else {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), item.getCommand().replaceAll("%player%", owner.getName()));
                }

                if (!item.getItem().hasItemMeta() || !item.getItem().getItemMeta().hasDisplayName() || item.getRarity().getPercentage() > 10)
                    return;

                TextComponent message = new TextComponent("");

                BaseComponent baseComponent = new TextComponent(Main.broadcastRare(owner.getName(), item.getRarity(), type));
                baseComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(stack.getItemMeta().getDisplayName())}));

                message.addExtra(baseComponent);

                Bukkit.getOnlinePlayers().forEach(player -> player.spigot().sendMessage(message));

                owner.getWorld().strikeLightningEffect(owner.getLocation());
            }
        }.runTaskLaterAsynchronously(Main.getInstance(), 40L);

        plugin.getController().remove(this);
    }

    public void itemRotate() {
        if (owner == null) return;
        if (items == null)
            items = new HashMap<>();

        i++;
        rotation++;

        owner.playSound(owner.getLocation(), Sound.CLICK, 1F, 1F);

        if (rotation == 2) {
            inv.setItem(11, items.get(10).getItem());
            items.put(11, items.get(10));

        } else if (rotation == 3) {
            inv.setItem(12, items.get(11).getItem());
            items.put(12, items.get(11));

            inv.setItem(11, items.get(10).getItem());
            items.put(11, items.get(10));
        } else if (rotation == 4) {
            inv.setItem(13, items.get(12).getItem());
            items.put(13, items.get(12));

            inv.setItem(12, items.get(11).getItem());
            items.put(12, items.get(11));

            inv.setItem(11, items.get(10).getItem());
            items.put(11, items.get(10));
        } else if (rotation == 5) {
            inv.setItem(14, items.get(13).getItem());
            items.put(14, items.get(13));

            inv.setItem(13, items.get(12).getItem());
            items.put(13, items.get(12));

            inv.setItem(12, items.get(11).getItem());
            items.put(12, items.get(11));

            inv.setItem(11, items.get(10).getItem());
            items.put(11, items.get(10));
        } else if (rotation == 6) {
            inv.setItem(15, items.get(14).getItem());
            items.put(15, items.get(14));

            inv.setItem(14, items.get(13).getItem());
            items.put(14, items.get(13));

            inv.setItem(13, items.get(12).getItem());
            items.put(13, items.get(12));

            inv.setItem(12, items.get(11).getItem());
            items.put(12, items.get(11));

            inv.setItem(11, items.get(10).getItem());
            items.put(11, items.get(10));
        } else if (rotation == 7) {
            inv.setItem(16, items.get(15).getItem());
            items.put(16, items.get(15));

            inv.setItem(15, items.get(14).getItem());
            items.put(15, items.get(14));

            inv.setItem(14, items.get(13).getItem());
            items.put(14, items.get(13));

            inv.setItem(13, items.get(12).getItem());
            items.put(13, items.get(12));

            inv.setItem(12, items.get(11).getItem());
            items.put(12, items.get(11));

            inv.setItem(11, items.get(10).getItem());
            items.put(11, items.get(10));
        } else if (rotation > 7) {
            items.remove(16);

            inv.setItem(16, items.get(15).getItem());
            items.put(16, items.get(15));

            inv.setItem(15, items.get(14).getItem());
            items.put(15, items.get(14));

            inv.setItem(14, items.get(13).getItem());
            items.put(14, items.get(13));

            inv.setItem(13, items.get(12).getItem());
            items.put(13, items.get(12));

            inv.setItem(12, items.get(11).getItem());
            items.put(12, items.get(11));

            inv.setItem(11, items.get(10).getItem());
            items.put(11, items.get(10));
        }

        BoxItem randomItem = randomItem();

        inv.setItem(10, randomItem.getItem());
        items.put(10, randomItem);

        if (i == 40){
            win(inv.getItem(13));
        }

    }

    private BoxItem randomItem() {
        BoxItem item = type.getItems().get(new Random().nextInt(type.getItems().size()));

        if (new Random().nextInt(100) <= item.getRarity().getPercentage()) {
            return item;
        } else {
            return randomItem();
        }
    }

    private void fillGlass(Inventory inventory) {
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