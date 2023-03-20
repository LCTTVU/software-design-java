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
        System.out.println(this.unit);
        String res = this.name + "," + this.quantity.toString();
        if (!Objects.equals(this.unit,"") && !Objects.equals(this.unit,"No Unit")) {
            res += "," + this.unit + "\n";
        }
        return res;
    }
}
