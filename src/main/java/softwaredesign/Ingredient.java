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
        String res = this.name + "," + this.quantity.toString();
        if (Objects.equals(this.unit,"")) {
            res += "\n";
        }
        else res += "," + this.unit + "\n";
        return res;
    }
}
