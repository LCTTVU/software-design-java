package softwaredesign;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Recipe {
    String name;
    String description;
    Long time;
    List<String> tags;
    List<Ingredient> ingredients;
    List<Instruction> instructions;

    private static final String RECIPE_FILE_FORMAT = ".json";

    public Recipe(String name,
           String description,
           List<Ingredient> ingredients,
           List<Instruction> instructions,
           Long time,
           List<String> tags) {
        this.name = name;
        this.description = description;
        this.time = time;
        this.tags = tags;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public boolean isUnnamed() {
        return this.name == null;
    }

    public void fillEmptyFields() {
        if (this.description == null) this.description = "No Description";
        if (this.time == null) this.time = 0L;
        if (this.tags == null) this.tags = Collections.singletonList("");
        if (this.ingredients == null) this.ingredients = Collections.singletonList(new Ingredient("No ingredients",0L,""));
        if (this.instructions == null) this.instructions = Collections.singletonList(new Instruction("No instructions",""));
    }

    public void updateInstructions(List<Instruction> newInstructions) {
        this.instructions = newInstructions;
    }

    public void writeToFile(File path) {
        if (path == null) {
            path = new File(RecipeList.RECIPE_PATH ,this.name + RECIPE_FILE_FORMAT); //new recipe file if creating
        }
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.name + "," + this.description + "," + this.ingredients + "," + this.instructions + "," + this.time + "," + this.tags;
    }
}
