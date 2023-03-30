package softwaredesign;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Recipe {
    private String name;
    private String description;
    private Long time;
    private List<String> tags;
    private List<Ingredient> ingredients;
    private List<Instruction> instructions;

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
        return this.name.isBlank();
    }

    public void fillMissingAttributes() {
        if (this.description.isBlank()) this.description = "No Description";
        if (this.time == null) this.time = 0L;
        if (this.tags == null) this.tags = Collections.singletonList("");
        if (this.ingredients == null) this.ingredients = Collections.singletonList(new Ingredient("No ingredients",0L,""));
        if (this.instructions == null) this.instructions = Collections.singletonList(new Instruction("No instructions",""));
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getDescription() {
        return this.description;
    }

    public Long getTime() {
        return this.time;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public List<String> getIngredientsString() {
        ArrayList<String> res = new ArrayList<>();
        for (Ingredient i : this.ingredients) {
            res.add(i.toString());
        }
        return res;
    }


    public List<Instruction> getInstructions() {
        return this.instructions;
    }

    public void updateAnnotations(List<Instruction> newInstructions) {
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
}
