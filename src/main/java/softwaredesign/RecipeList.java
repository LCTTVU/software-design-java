package softwaredesign;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public abstract class RecipeList {

    public static final String RECIPE_PATH = "./recipes";

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

        int unnamedCount = 1; //this is to create a generic name for any recipe json that does not have "name" attribute
        for (File file : listOfFiles) {
            Recipe recipe = jsonToRecipe(file);
            if (recipe == null) continue; //skip

            if (recipe.isUnnamed()) {
                recipe.name = "Unnamed Recipe " + unnamedCount;
                unnamedCount++;
            }
            //fill any empty attributes and write back to json file
            recipe.fillEmptyFields();
            recipe.writeToFile(file.toString());

            recipes.put(file.toString(),recipe);
        }
        return recipes;
    }

    public static List<String> getRecipeNames(Map<String,Recipe> recipes) {
        ArrayList<String> names = new ArrayList<>();
        for (Recipe recipe : recipes.values()) {
            names.add(recipe.name);
        }
        Collections.sort(names);
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

    //This method removes all irrelevant information (whitespace, extra delimiters) from input string
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


    public static void saveRecipe(String path, String inputName, String inputDesc, String inputTime, String inputTags, String inputIngr, String inputInst) throws NullPointerException, IndexOutOfBoundsException, NumberFormatException {

        String name =           inputName.strip();
        String desc =           inputDesc.strip();
        String timeStr =        inputTime.strip();
        String tagsStr =        inputTags.strip();
        String ingredientStr =  inputIngr.strip();
        String instructionStr = inputInst.strip();


        if (name.isBlank())             errorEmptyStringAt("Name");
        if (desc.isBlank())             errorEmptyStringAt("Description");
        if (timeStr.isBlank())          errorEmptyStringAt("Time");
        if (tagsStr.isBlank())          errorEmptyStringAt("Tags");
        if (ingredientStr.isBlank())    errorEmptyStringAt("Ingredients");
        if (instructionStr.isBlank())   errorEmptyStringAt("Instructions");

        Long time = tryParse(timeStr,"Time");

        List<String> tags = tokenize(tagsStr,",");

        //Convert ingredients input to ingredients arraylist
        List<String> ingTokens = tokenize(ingredientStr, "\n");
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (String token : ingTokens) {
            List<String> properties = tokenize(token, ",");
            String ingName = properties.get(0);
            //this line will throw IndexOutOfBoundsException if the user does not specify the quantity
            Long ingQuantity = tryParse(properties.get(1),"Ingredient quantity");

            String ingUnit;
            if (properties.size() < 3)  ingUnit = "No unit";
            else                        ingUnit = properties.get(2);

            ingredients.add(new Ingredient(ingName,ingQuantity,ingUnit));
        }

        // Convert instructions input to instructions arraylist
        List<String> insTokens = tokenize(instructionStr,"\n");
        LinkedList<Instruction> instructions = new LinkedList<>();
        for (String token: insTokens) {
            instructions.add(new Instruction(token, ""));
        }

        Recipe newRecipe = new Recipe(name,desc,ingredients,instructions,time,tags);
        newRecipe.writeToFile(path);
    }

    public static void deleteRecipe(String recipePath) throws IOException {
        Files.delete(Path.of(recipePath));
    }
}
