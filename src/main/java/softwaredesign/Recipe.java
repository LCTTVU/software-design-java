package softwaredesign;

import java.util.List;

public class Recipe {
    String name;
    String description;
    List<Ingredient> ingredients;
    List<Instruction> instructions;
    String time;
    List<String> tags;

    public Recipe(String name,
           String description,
           List<Ingredient> ingredients,
           List<Instruction> instructions,
           String time,
           List<String> tags) {
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
