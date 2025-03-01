package br.com.stenoxz.caixas.box;

import br.com.stenoxz.caixas.Main;
import br.com.stenoxz.caixas.item.BoxItem;
import br.com.stenoxz.caixas.type.BoxType;
import br.com.stenoxz.caixas.utils.ItemBuilder;
import br.com.stenoxz.caixas.utils.TextUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class Box implements Listener, InventoryHolder {
   private BoxType type;
   private final Main plugin;
   private final Player owner;
   private int i;
   private int rotation;
   private Map<Integer, BoxItem> items;
   private Inventory inv;

   public ItemStack getItem() {
      return this.type.getIcon();
   }

   public void openBox() {
      if (this.owner != null) {
         this.inv = Bukkit.createInventory(this, 27, TextUtils.format(Main.inventoryTitle, this.type));
         this.fillGlass(this.inv);
         this.owner.openInventory(this.inv);
         this.plugin.getController().create(this);
      }
   }

   public void win(ItemStack stack) {
      if (this.owner != null) {
         BoxItem item = null;

         for (BoxItem i : this.type.getItems()) {
            if (i.getItem().equals(stack)) {
               item = i;
               break;
            }
         }

         if (this.owner.getOpenInventory().getTopInventory().equals(this.inv)) {
            this.owner.closeInventory();
         }

         if (item != null) {
            if (item.getCommand() != null && !item.getCommand().equalsIgnoreCase("")) {
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), item.getCommand().replaceAll("%player%", this.owner.getName()));
            } else if (this.owner.getInventory().firstEmpty() == -1) {
               this.owner.getWorld().dropItem(this.owner.getLocation(), stack);
            } else {
               this.owner.getInventory().addItem(new ItemStack[]{stack});
            }

            if (item.getItem().hasItemMeta() && item.getItem().getItemMeta().hasDisplayName() && item.getRarity().getPercentage() <= 10) {
               TextComponent message = new TextComponent("");
               BaseComponent baseComponent = new TextComponent(Main.broadcastRare(this.owner.getName(), item.getRarity(), this.type));
               baseComponent.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(stack.getItemMeta().getDisplayName())}));
               message.addExtra(baseComponent);
               Bukkit.getOnlinePlayers().forEach(player -> player.spigot().sendMessage(message));
               this.owner.getWorld().strikeLightningEffect(this.owner.getLocation());
               this.plugin.getController().remove(this);
            }
         }
      }
   }

   public void itemRotate() {
      if (this.owner != null) {
         if (this.items == null) {
            this.items = new HashMap();
         }

         this.i++;
         if (this.i < 40) {
            this.rotation++;
            this.owner.playSound(this.owner.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
            if (this.rotation == 2) {
               this.inv.setItem(11, ((BoxItem)this.items.get(10)).getItem());
               this.items.put(11, this.items.get(10));
            } else if (this.rotation == 3) {
               this.inv.setItem(12, ((BoxItem)this.items.get(11)).getItem());
               this.items.put(12, this.items.get(11));
               this.inv.setItem(11, ((BoxItem)this.items.get(10)).getItem());
               this.items.put(11, this.items.get(10));
            } else if (this.rotation == 4) {
               this.inv.setItem(13, ((BoxItem)this.items.get(12)).getItem());
               this.items.put(13, this.items.get(12));
               this.inv.setItem(12, ((BoxItem)this.items.get(11)).getItem());
               this.items.put(12, this.items.get(11));
               this.inv.setItem(11, ((BoxItem)this.items.get(10)).getItem());
               this.items.put(11, this.items.get(10));
            } else if (this.rotation == 5) {
               this.inv.setItem(14, ((BoxItem)this.items.get(13)).getItem());
               this.items.put(14, this.items.get(13));
               this.inv.setItem(13, ((BoxItem)this.items.get(12)).getItem());
               this.items.put(13, this.items.get(12));
               this.inv.setItem(12, ((BoxItem)this.items.get(11)).getItem());
               this.items.put(12, this.items.get(11));
               this.inv.setItem(11, ((BoxItem)this.items.get(10)).getItem());
               this.items.put(11, this.items.get(10));
            } else if (this.rotation == 6) {
               this.inv.setItem(15, ((BoxItem)this.items.get(14)).getItem());
               this.items.put(15, this.items.get(14));
               this.inv.setItem(14, ((BoxItem)this.items.get(13)).getItem());
               this.items.put(14, this.items.get(13));
               this.inv.setItem(13, ((BoxItem)this.items.get(12)).getItem());
               this.items.put(13, this.items.get(12));
               this.inv.setItem(12, ((BoxItem)this.items.get(11)).getItem());
               this.items.put(12, this.items.get(11));
               this.inv.setItem(11, ((BoxItem)this.items.get(10)).getItem());
               this.items.put(11, this.items.get(10));
            } else if (this.rotation == 7) {
               this.inv.setItem(16, ((BoxItem)this.items.get(15)).getItem());
               this.items.put(16, this.items.get(15));
               this.inv.setItem(15, ((BoxItem)this.items.get(14)).getItem());
               this.items.put(15, this.items.get(14));
               this.inv.setItem(14, ((BoxItem)this.items.get(13)).getItem());
               this.items.put(14, this.items.get(13));
               this.inv.setItem(13, ((BoxItem)this.items.get(12)).getItem());
               this.items.put(13, this.items.get(12));
               this.inv.setItem(12, ((BoxItem)this.items.get(11)).getItem());
               this.items.put(12, this.items.get(11));
               this.inv.setItem(11, ((BoxItem)this.items.get(10)).getItem());
               this.items.put(11, this.items.get(10));
            } else if (this.rotation > 7) {
               this.items.remove(16);
               this.inv.setItem(16, ((BoxItem)this.items.get(15)).getItem());
               this.items.put(16, this.items.get(15));
               this.inv.setItem(15, ((BoxItem)this.items.get(14)).getItem());
               this.items.put(15, this.items.get(14));
               this.inv.setItem(14, ((BoxItem)this.items.get(13)).getItem());
               this.items.put(14, this.items.get(13));
               this.inv.setItem(13, ((BoxItem)this.items.get(12)).getItem());
               this.items.put(13, this.items.get(12));
               this.inv.setItem(12, ((BoxItem)this.items.get(11)).getItem());
               this.items.put(12, this.items.get(11));
               this.inv.setItem(11, ((BoxItem)this.items.get(10)).getItem());
               this.items.put(11, this.items.get(10));
            }

            BoxItem randomItem = this.randomItem();
            this.inv.setItem(10, randomItem.getItem());
            this.items.put(10, randomItem);
         } else if (this.i == 48) {
            this.win(this.inv.getItem(13));
         }
      }
   }

   private BoxItem randomItem() {
      BoxItem item = (BoxItem)this.type.getItems().get(new Random().nextInt(this.type.getItems().size()));
      return new Random().nextInt(100) <= item.getRarity().getPercentage() ? item : this.randomItem();
   }

   private void fillGlass(Inventory inventory) {
      inventory.setItem(0, new ItemBuilder().setMaterial(Material.LEGACY_STAINED_GLASS_PANE).setDurability(14).getStack());
      inventory.setItem(9, new ItemBuilder().setMaterial(Material.LEGACY_STAINED_GLASS_PANE).setDurability(14).getStack());
      inventory.setItem(18, new ItemBuilder().setMaterial(Material.LEGACY_STAINED_GLASS_PANE).setDurability(14).getStack());
      inventory.setItem(1, new ItemBuilder().setMaterial(Material.LEGACY_STAINED_GLASS_PANE).setDurability(1).getStack());
      inventory.setItem(19, new ItemBuilder().setMaterial(Material.LEGACY_STAINED_GLASS_PANE).setDurability(1).getStack());
      inventory.setItem(2, new ItemBuilder().setMaterial(Material.LEGACY_STAINED_GLASS_PANE).setDurability(4).getStack());
      inventory.setItem(20, new ItemBuilder().setMaterial(Material.LEGACY_STAINED_GLASS_PANE).setDurability(4).getStack());
      inventory.setItem(3, new ItemBuilder().setMaterial(Material.LEGACY_STAINED_GLASS_PANE).setDurability(5).getStack());
      inventory.setItem(21, new ItemBuilder().setMaterial(Material.LEGACY_STAINED_GLASS_PANE).setDurability(5).getStack());
      inventory.setItem(4, new ItemBuilder().setMaterial(Material.LEGACY_STAINED_GLASS_PANE).setDurability(15).getStack());
      inventory.setItem(22, new ItemBuilder().setMaterial(Material.LEGACY_STAINED_GLASS_PANE).setDurability(15).getStack());
      inventory.setItem(5, new ItemBuilder().setMaterial(Material.LEGACY_STAINED_GLASS_PANE).setDurability(11).getStack());
      inventory.setItem(23, new ItemBuilder().setMaterial(Material.LEGACY_STAINED_GLASS_PANE).setDurability(11).getStack());
      inventory.setItem(6, new ItemBuilder().setMaterial(Material.LEGACY_STAINED_GLASS_PANE).setDurability(10).getStack());
      inventory.setItem(24, new ItemBuilder().setMaterial(Material.LEGACY_STAINED_GLASS_PANE).setDurability(10).getStack());
      inventory.setItem(7, new ItemBuilder().setMaterial(Material.LEGACY_STAINED_GLASS_PANE).setDurability(6).getStack());
      inventory.setItem(25, new ItemBuilder().setMaterial(Material.LEGACY_STAINED_GLASS_PANE).setDurability(6).getStack());
      inventory.setItem(8, new ItemBuilder().setMaterial(Material.LEGACY_STAINED_GLASS_PANE).setDurability(9).getStack());
      inventory.setItem(17, new ItemBuilder().setMaterial(Material.LEGACY_STAINED_GLASS_PANE).setDurability(9).getStack());
      inventory.setItem(26, new ItemBuilder().setMaterial(Material.LEGACY_STAINED_GLASS_PANE).setDurability(9).getStack());
   }

   Box(BoxType type, Main plugin, Player owner, int i, int rotation, Map<Integer, BoxItem> items, Inventory inv) {
      this.type = type;
      this.plugin = plugin;
      this.owner = owner;
      this.i = i;
      this.rotation = rotation;
      this.items = items;
      this.inv = inv;
   }

   public static BoxBuilder builder() {
      return new BoxBuilder();
   }

   public BoxType getType() {
      return this.type;
   }

   public Main getPlugin() {
      return this.plugin;
   }

   public Player getOwner() {
      return this.owner;
   }

   public int getI() {
      return this.i;
   }

   public int getRotation() {
      return this.rotation;
   }

   public Map<Integer, BoxItem> getItems() {
      return this.items;
   }

   public void setType(BoxType type) {
      this.type = type;
   }

@Override
public Inventory getInventory() {
	// TODO Auto-generated method stub
	return inv;
}
}