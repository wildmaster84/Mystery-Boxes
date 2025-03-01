package br.com.stenoxz.caixas.type;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import br.com.stenoxz.caixas.item.BoxItem;

public class BoxTypeBuilder {
	   private String name;
	   private String displayName;
	   private ItemStack icon;
	   private List<BoxItem> items;

	   BoxTypeBuilder() {
	   }

	   public BoxTypeBuilder name(String name) {
	      this.name = name;
	      return this;
	   }

	   public BoxTypeBuilder displayName(String displayName) {
	      this.displayName = displayName;
	      return this;
	   }

	   public BoxTypeBuilder icon(ItemStack icon) {
	      this.icon = icon;
	      return this;
	   }

	   public BoxTypeBuilder items(List<BoxItem> items) {
	      this.items = items;
	      return this;
	   }

	   public BoxType build() {
	      return new BoxType(this.name, this.displayName, this.icon, this.items);
	   }

	   public String toString() {
	      return "BoxType.BoxTypeBuilder(name=" + this.name + ", displayName=" + this.displayName + ", icon=" + this.icon + ", items=" + this.items + ")";
	   }

	}
