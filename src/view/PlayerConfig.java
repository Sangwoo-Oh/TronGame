package view;

public class PlayerConfig {
     private final String name;
     private final String color;

     public PlayerConfig(String name, String color) {
         this.name = name;
         this.color = color;
     }

     public String getName() {
         return name;
     }

     public String getColor() {
         return color;
     }
}
