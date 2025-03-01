package br.com.stenoxz.caixas.item.rarity;

public class BoxItemRarity {
   private String name;
   private String displayName;
   private int percentage;

   BoxItemRarity(String name, String displayName, int percentage) {
      this.name = name;
      this.displayName = displayName;
      this.percentage = percentage;
   }

   public static BoxItemRarityBuilder builder() {
      return new BoxItemRarityBuilder();
   }

   public String getName() {
      return this.name;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public int getPercentage() {
      return this.percentage;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setDisplayName(String displayName) {
      this.displayName = displayName;
   }

   public void setPercentage(int percentage) {
      this.percentage = percentage;
   }
}