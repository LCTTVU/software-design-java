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

    public static Map<String,Recipe> getRecipes() {
        HashMap<String,Recipe> recipes = new HashMap<>();

        File folder = new File(RECIPE_PATH);
        File[] listOfFiles = folder.listFiles();

        assert listOfFiles != null;
        for (File file : listOfFiles) {
            recipes.put(file.toString(),jsonToRecipe(file));
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
        for (String filename : recipes.keySet()) {
            res = filename;
            String recipeName = recipes.get(filename).name;
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


    public static void createRecipe(String path,String nameStr, String descStr, String ingStr, String insStr, String timeStr, String tagStr) throws NullPointerException, IndexOutOfBoundsException, NumberFormatException {

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
        LinkedList<Instruction> instructions = new LinkedList<>();
        for (String token: insTokens) {
            instructions.add(new Instruction(token, ""));
        }
        
        if (timeStr.isBlank()) errorEmptyStringAt("Time");
        Long time = tryParse(timeStr.strip(),"Time");

        if (tagStr.isBlank()) errorEmptyStringAt("Tags");
        List<String> tags = tokenize(tagStr,",");

        Recipe newRecipe = new Recipe(name,desc,ingredients,instructions,time,tags);

        File location;
        if (path == null) {
            location = new File(RECIPE_PATH ,name + RECIPE_FILE_FORMAT); //standard write to file stuff
        }
        else {
            location = new File(path);
        }
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(location)) {
            gson.toJson(newRecipe, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteRecipe(String name) throws IOException {
        File file = new File(name);
        if (!file.delete()) throw new IOException("Unable to delete");
    }
}
