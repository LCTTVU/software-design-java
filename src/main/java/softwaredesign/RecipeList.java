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

    private RecipeList() {
        throw new IllegalStateException("Utility class");
    }


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

    public static Map<String,Recipe> getRecipes() {
        HashMap<String,Recipe> recipes = new HashMap<>();

        File folder = new File(RECIPE_PATH);
        File[] listOfFiles = folder.listFiles();

        assert listOfFiles != null;
        for (File file : listOfFiles) {
            Recipe recipe = jsonToRecipe(file);
            if (recipe != null && !recipe.emptyFields()) {
                recipes.put(file.toString(),recipe);
            }

        }

        return recipes;
    }

    public static List<String> getRecipeNames(Map<String,Recipe> recipes) {
        ArrayList<String> names = new ArrayList<>();
        for (Recipe recipe : recipes.values()) {
            names.add(recipe.name);
        }
        return names;
    }

    public static String getFilenameFromRecipeName(Map<String,Recipe> recipes, String name) {
        String res = null;
        for (Map.Entry<String,Recipe> entry : recipes.entrySet()) {
            res = entry.getKey();
            String recipeName = entry.getValue().name;
            if (Objects.equals(recipeName,name)) break;
        }
        return res;
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


    public static Recipe createRecipe(String inputName, String inputDesc, String inputIngr, String inputInst, String inputTime, String inputTags) throws NullPointerException, IndexOutOfBoundsException, NumberFormatException {

        String name = inputName.strip();
        if (name.isBlank()) errorEmptyStringAt("Name");

        String desc = inputDesc.strip();
        if (desc.isBlank()) errorEmptyStringAt("Description");

        //Convert ingredients input to ingredients arraylist
        String ingredientsStr = inputIngr.strip();
        if (ingredientsStr.isBlank()) errorEmptyStringAt("Ingredients");
        List<String> ingTokens = tokenize(ingredientsStr, "\n");
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
        String instructionsStr = inputInst.strip();
        if (instructionsStr.isBlank()) errorEmptyStringAt("Instructions");
        List<String> insTokens = tokenize(instructionsStr,"\n");
        LinkedList<Instruction> instructions = new LinkedList<>();
        for (String token: insTokens) {
            instructions.add(new Instruction(token, ""));
        }

        String timeStr = inputTime.strip();
        if (timeStr.isBlank()) errorEmptyStringAt("Time");
        Long time = tryParse(timeStr,"Time");

        String tagsStr = inputTags.strip();
        if (tagsStr.isBlank()) errorEmptyStringAt("Tags");
        List<String> tags = tokenize(tagsStr,",");

        return new Recipe(name,desc,ingredients,instructions,time,tags);
    }

    public static void writeToFile(String path, Recipe recipe) {
        File location;
        if (path == null) {
            location = new File(RECIPE_PATH ,recipe.name + RECIPE_FILE_FORMAT); //new recipe file if creating
        }
        else {
            location = new File(path);  //overwrite old recipe if editing
        }
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(location)) {
            gson.toJson(recipe, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteRecipe(String name) throws IOException {
        File file = new File(name);
        if (!file.delete()) throw new IOException("Unable to delete");
    }
}
