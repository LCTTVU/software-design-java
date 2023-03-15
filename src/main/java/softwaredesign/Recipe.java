package softwaredesign;

import java.util.ArrayList;

public class Recipe {
    String name;
    String description;
    ArrayList<Ingredient> ingredients;
    ArrayList<Instruction> instructions;
    String time;
    ArrayList<String> tags;

    public Recipe(String name,
           String description,
           ArrayList<Ingredient> ingredients,
           ArrayList<Instruction> instructions,
           String time,
           ArrayList<String> tags) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.time = time;
        this.tags = tags;
    }

    @Override
    public String toString() {
        return this.name + "," + this.description + "," + this.ingredients + "," + this.instructions + "," + this.time + "," + this.tags;
    }
}
