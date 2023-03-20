package softwaredesign;

import java.util.List;

public class Recipe {
    String name;
    String description;
    List<Ingredient> ingredients;
    List<Instruction> instructions;
    Long time;
    List<String> tags;

    public Recipe(String name,
           String description,
           List<Ingredient> ingredients,
           List<Instruction> instructions,
           Long time,
           List<String> tags) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.time = time;
        this.tags = tags;
    }

    public boolean hasEmptyFields() {
        return (this.name == null || this.description == null || this.ingredients == null
                || this.instructions == null || this.time == null || this.tags == null);
    }

    public void updateInstructions(String path, List<Instruction> newInstructions) {
        this.instructions = newInstructions;
        RecipeList.writeToFile(path,this);
    }

    @Override
    public String toString() {
        return this.name + "," + this.description + "," + this.ingredients + "," + this.instructions + "," + this.time + "," + this.tags;
    }
}
