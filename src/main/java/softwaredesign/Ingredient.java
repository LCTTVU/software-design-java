package softwaredesign;

public class Ingredient {
    String name;
    int quantity;
    String unit;
    Ingredient(String name, int quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }
    @Override
    public String toString() {
        return "{" + this.name + "," + this.quantity + "," + this.unit + "}";
    }
}
