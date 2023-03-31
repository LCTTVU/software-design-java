package softwaredesign;

import java.util.Objects;

public class Ingredient {
    private final String name;
    private final Long quantity;
    private final String unit;

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
