package softwaredesign;


import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.*;


public class RecipeList {

    private static final String RECIPE_PATH = "./recipes";
    private static final String RECIPE_FILE_FORMAT = ".json";

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
        File file = new File(RECIPE_PATH,name + RECIPE_FILE_FORMAT);
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

    private void errorEmptyStringAt(String type) throws NullPointerException {
        throw new NullPointerException("Please input " + type);
    }

    private Long tryParse(String input, String type) throws NumberFormatException {
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new NumberFormatException("Please input a valid number for " + type);
            }
        }
        return Long.parseLong(input);
    }


    public void createRecipe(String nameStr, String descStr, String ingStr, String insStr, String timeStr, String tagStr) throws NullPointerException, IndexOutOfBoundsException, NumberFormatException {

        if (nameStr.isBlank()) errorEmptyStringAt("Name");
        String name = nameStr.strip();
        if (descStr.isBlank()) errorEmptyStringAt("Description");
        String desc = descStr.strip();

        //Convert ingredients input to ingredients arraylist
        if (ingStr.isBlank()) errorEmptyStringAt("Ingredients");
        List<String> ingTokens = tokenize(ingStr, "\n");
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (String token : ingTokens) {
            List<String> properties = tokenize(token, ",");
            String ingName = properties.get(0);
            Long ingQuantity = tryParse(properties.get(1),"Ingredient quantity");
            String ingUnit;
            if (properties.size() < 3) ingUnit = "No unit";
            else ingUnit = properties.get(2);
            ingredients.add(new Ingredient(ingName,ingQuantity,ingUnit));
        }

        // Convert instructions input to instructions arraylist
        if (insStr.isBlank()) errorEmptyStringAt("Instructions");
        List<String> insTokens = tokenize(insStr,"\n");
        ArrayList<Instruction> instructions = new ArrayList<>();
        for (String token: insTokens) {
            instructions.add(new Instruction(token, ""));
        }
        
        if (timeStr.isBlank()) errorEmptyStringAt("Time");
        Long time = tryParse(timeStr.strip(),"Time");

        if (tagStr.isBlank()) errorEmptyStringAt("Tags");
        List<String> tags = tokenize(tagStr,",");

        Recipe newRecipe = new Recipe(name,desc,ingredients,instructions,time,tags);

        //write to file
        File location = new File(RECIPE_PATH ,name + RECIPE_FILE_FORMAT);
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(location)) {
            gson.toJson(newRecipe, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteRecipe(String name) throws IOException {
        File file = new File(RECIPE_PATH, name + RECIPE_FILE_FORMAT);
        if (!file.delete()) throw new IOException("Unable to delete recipe");
    }
}
