package softwaredesign;

import java.util.Objects;

public class Ingredient {
    String name;
    Long quantity;
    String unit;
    Ingredient(String name, Long quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }
    @Override
    public String toString() {
        String u = (Objects.equals(this.unit,"")) ? ("") : ("," + this.unit);
        return this.name + "," + this.quantity + u + "\n";
    }
}
