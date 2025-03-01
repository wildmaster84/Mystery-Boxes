package br.com.stenoxz.caixas.item.rarity;

public class BoxItemRarityBuilder {
   private String name;
   private String displayName;
   private int percentage;

   BoxItemRarityBuilder() {
   }

   public BoxItemRarityBuilder name(String name) {
      this.name = name;
      return this;
   }

   public BoxItemRarityBuilder displayName(String displayName) {
      this.displayName = displayName;
      return this;
   }

   public BoxItemRarityBuilder percentage(int percentage) {
      this.percentage = percentage;
      return this;
   }

   public BoxItemRarity build() {
      return new BoxItemRarity(this.name, this.displayName, this.percentage);
   }

   public String toString() {
      return "BoxItemRarity.BoxItemRarityBuilder(name=" + this.name + ", displayName=" + this.displayName + ", percentage=" + this.percentage + ")";
   }
}