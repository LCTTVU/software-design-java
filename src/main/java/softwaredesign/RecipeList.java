package softwaredesign;


import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.*;


public abstract class RecipeList {

    private static final String RECIPE_PATH = "./recipes";
    private static final String RECIPE_FILE_FORMAT = ".json";

    private static Recipe jsonToRecipe(File file) {
        Recipe recipe = null;
        if (file.isFile()) {
            try {
                Gson gson = new Gson();
                Scanner myReader = new Scanner(file);       //standard file reader stuff
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

    public static List<Recipe> getRecipes() {

        ArrayList<Recipe> recipeArrayList = new ArrayList<>();

        File folder = new File(RECIPE_PATH);
        File[] listOfFiles = folder.listFiles();


        assert listOfFiles != null;
        for (File file : listOfFiles) {
            recipeArrayList.add(jsonToRecipe(file));
        }
        return recipeArrayList;
    }


    /*
    This method calls getRecipes() again and searches the list for the correct name,
    instead of getting the File directly from the name.
    If recipe.name (recipeName) =/= .json file name, trying to get the File will cause NullPointerException

    For example:
    example.json
        {"name": "diffNameFromFile",...}

    trying to get diffNameFromFile.json will cause NullPointerException because it does not exist

    The system will display recipe name instead of file name, making the two independent
     */
    public static Recipe getRecipe(String recipeName) {
        Recipe recipe = null;
        List<Recipe> recipeList = getRecipes();
        for (Recipe r : recipeList) {
            if (Objects.equals(recipeName,r.name)) {
               recipe = r;
            }
        }
        return recipe;
    }


    public static List<String> getRecipeNames() {
        List<Recipe> recipeList = getRecipes(); //same reasons as getRecipe()
        ArrayList<String> recipeNames = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            recipeNames.add(recipe.name);
        }
        return recipeNames;
    }

    /*
    This function removes all irrelevant information (whitespace, extra delimiters) from input string
     */
    private static List<String> tokenize(String input, String regex){
        String[] tokens = input.strip().split(regex);
        ArrayList<String> res = new ArrayList<>();
        for (String token : tokens) {
            if (!token.strip().isBlank()) res.add(token.strip());
        }
        return res;
    }


    //input validation
    private static void errorEmptyStringAt(String type) throws NullPointerException {
        throw new NullPointerException("Please input " + type);
    }

    private static Long tryParse(String input, String type) throws NumberFormatException {
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new NumberFormatException("Please input a valid number for " + type);
            }
        }
        return Long.parseLong(input);
    }


    public static void createRecipe(String nameStr, String descStr, String ingStr, String insStr, String timeStr, String tagStr) throws NullPointerException, IndexOutOfBoundsException, NumberFormatException {

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

            Long ingQuantity = tryParse(properties.get(1),"Ingredient quantity");  //this line will throw IndexOutOfBoundsException if the user does not specify the quantity

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


        File location = new File(RECIPE_PATH ,name + RECIPE_FILE_FORMAT); //standard write to file stuff
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(location)) {
            gson.toJson(newRecipe, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteRecipe(String name) throws IOException {
        File file = new File(RECIPE_PATH, name + RECIPE_FILE_FORMAT);
        if (!file.delete()) throw new IOException("Unable to delete recipe");
    }
}
