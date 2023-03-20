package softwaredesign;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.*;

public class RecipeList {

    public static final String RECIPE_PATH = "./recipes";
    private Map<File,Recipe> recipes;

    private static RecipeList instance;

    private RecipeList() {
        updateRecipes();
    }

    public static RecipeList getInstance() {
        if (instance == null) {
            instance = new RecipeList();
        }
        return instance;
    }

    public void updateRecipes() {
        HashMap<File,Recipe> recipesMap = new HashMap<>();

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
            recipe.writeToFile(file);

            recipesMap.put(file,recipe);
        }
        this.recipes = recipesMap;
    }

    private Recipe jsonToRecipe(File file) {
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

    public Map<File,Recipe> getRecipes() {
        updateRecipes();
        return recipes;
    }

    public List<String> getRecipeNameList() {
        ArrayList<String> names = new ArrayList<>();
        for (Recipe recipe : recipes.values()) {
            names.add(recipe.name);
        }
        Collections.sort(names);
        return names;
    }

    public File getFilename(String name) {
        File res = null;
        for (Map.Entry<File,Recipe> entry : recipes.entrySet()) {
            res = entry.getKey();
            String recipeName = entry.getValue().name;
            if (Objects.equals(recipeName,name)) break;
        }
        return res;
    }

    //input validation methods
    private static String inputCheck(String input) throws NullPointerException {
        String res = input.strip();
        if (res.isBlank()) throw new NullPointerException("Please fill all fields");
        return res;
    }

    private static Long tryParse(String input, String type) throws NumberFormatException {
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new NumberFormatException("Please input a valid number for " + type);
            }
        }
        return Long.parseLong(input);
    }

    private static boolean unitSpecified(List<String> input) {
        return input.size() > 2;
    }

    /*
    This method removes all irrelevant information (whitespace, extra delimiters, empty tokens) from input string
    */
    private static List<String> tokenize(String input, String regex){
        String[] tokens = input.strip().split(regex);
        ArrayList<String> res = new ArrayList<>();
        for (String token : tokens) {
            if (!token.strip().isBlank()) res.add(token.strip());
        }
        return res;
    }

    public static void saveRecipe(File path, String inputName, String inputDesc, String inputTime, String inputTags, String inputIngr, String inputInst) throws NullPointerException, IndexOutOfBoundsException, NumberFormatException {

        String name =           inputCheck(inputName);
        String desc =           inputCheck(inputDesc);
        String timeStr =        inputCheck(inputTime);
        String tagsStr =        inputCheck(inputTags);
        String ingredientStr =  inputCheck(inputIngr);
        String instructionStr = inputCheck(inputInst);

        Long time = tryParse(timeStr,"Time");

        List<String> tags = tokenize(tagsStr,",");

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        //Convert ingredients input to ingredients arraylist
        for (String ingredient : tokenize(ingredientStr, "\n")) {
            List<String> properties = tokenize(ingredient, ",");
            String ingName = properties.get(0);
            //this line will throw IndexOutOfBoundsException if the user does not specify the quantity
            Long ingQuantity = tryParse(properties.get(1),"Ingredient quantity");

            String ingUnit;
            if (unitSpecified(properties)) ingUnit = properties.get(2);
            else ingUnit = "No Unit";

            ingredients.add(new Ingredient(ingName,ingQuantity,ingUnit));
        }

        LinkedList<Instruction> instructions = new LinkedList<>();
        // Convert instructions input to instructions arraylist
        for (String instruction : tokenize(instructionStr,"\n")) {
            instructions.add(new Instruction(instruction, ""));
        }

        Recipe newRecipe = new Recipe(name,desc,ingredients,instructions,time,tags);
        newRecipe.writeToFile(path);

    }

    public static void deleteRecipe(File recipePath) throws IOException {
        if (!recipePath.delete()) {
            throw new IOException("Unable to delete recipe");
        }
    }
}
