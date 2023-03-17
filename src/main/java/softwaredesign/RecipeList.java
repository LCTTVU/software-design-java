package softwaredesign;


import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.*;


public class RecipeList {

    private static final String RECIPE_PATH = "./recipes";

    private static RecipeList instance;
    private RecipeList() {}

    public static RecipeList getInstance() {
        if (instance == null) {
            instance = new RecipeList();
        }
        return instance;
    }

    private Recipe jsonToRecipe(File file) {
        Recipe recipe = null;
        if (file.isFile()) {
            try {
                Gson gson = new Gson();
                Scanner myReader = new Scanner(file);
                StringBuilder data = new StringBuilder();
                while (myReader.hasNextLine()) {
                    String line = myReader.nextLine();
                    data.append(line.strip());
                }
                recipe = gson.fromJson(data.toString(),Recipe.class);
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("File " + file.getName() + "not found.");
                e.printStackTrace();
            }
        }
        return recipe;
    }

    public List<Recipe> getRecipes() {

        ArrayList<Recipe> recipeArrayList = new ArrayList<>();

        File folder = new File(RECIPE_PATH);
        File[] listOfFiles = folder.listFiles();

        assert listOfFiles != null;
        for (File file : listOfFiles) {
            recipeArrayList.add(jsonToRecipe(file));
        }
        return recipeArrayList;
    }

    public Recipe getRecipe(String name) {
        File file = new File(RECIPE_PATH,name + ".json");
        return jsonToRecipe(file);
    }

    public List<String> getRecipeNames() {
        List<Recipe> recipeList = getRecipes();
        ArrayList<String> recipeNames = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            recipeNames.add(recipe.name);
        }
        return recipeNames;
    }

    private List<String> tokenize(String input, String regex){
        String[] tokens = input.strip().split(regex);
        ArrayList<String> res = new ArrayList<>();
        for (String token : tokens) {
            if (!token.strip().isBlank()) res.add(token.strip());
        }
        return res;
    }

    private void errorAt(String type) throws IllegalArgumentException {
        throw new IllegalArgumentException("Please input " + type);
    }

    public void createRecipe(String nameStr, String descStr, String ingStr, String insStr, String timeStr, String tagStr) throws IllegalArgumentException, IndexOutOfBoundsException {

        if (nameStr.isBlank()) errorAt("Name");
        if (descStr.isBlank()) errorAt("Description");

        //Convert ingredients input to ingredients arraylist
        if (ingStr.isBlank()) errorAt("Ingredients");
        List<String> ingTokens = tokenize(ingStr, "\n");
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (String token : ingTokens) {
            List<String> properties = tokenize(token, ",");
            String name = properties.get(0);
            String quantity = properties.get(1);
            String unit;
            if (properties.size() < 3) unit = null;
            else unit = properties.get(2);
            ingredients.add(new Ingredient(name,quantity,unit));
        }

        // Convert instructions input to instructions arraylist
        if (insStr.isBlank()) errorAt("Instructions");
        List<String> insTokens = tokenize(insStr,"\n");
        ArrayList<Instruction> instructions = new ArrayList<>();
        for (String token: insTokens) instructions.add(new Instruction(token,""));
        
        if (timeStr.isBlank()) errorAt("Time");

        if (tagStr.isBlank()) errorAt("Tags");
        ArrayList<String> tags = new ArrayList<>(tokenize(tagStr,","));

        Recipe newRecipe = new Recipe(nameStr,descStr,ingredients,instructions,timeStr,tags);

        //write to file
        File location = new File("./recipes/" + nameStr + ".json");
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(location)) {
            gson.toJson(newRecipe, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteRecipe(String name) {
        File file = new File(RECIPE_PATH, name + ".json");
        if (file.delete()) System.out.println("Deleted the file " + file.getName());
        else System.out.println("Failed to delete the file " + file.getName());
    }
}
