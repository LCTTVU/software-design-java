package softwaredesign;

public class Ingredient {
    String name;
    String quantity;
    String unit;
    Ingredient(String name, String quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }
    @Override
    public String toString() {
        return this.name + "," + this.quantity + "," + this.unit + "\n";
    }
}
