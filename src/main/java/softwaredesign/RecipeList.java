package softwaredesign;


import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;


public class RecipeList {
    private static RecipeList instance;
    private RecipeList() {}

    public static RecipeList getInstance() {
        if (instance == null) {
            instance = new RecipeList();
        }
        return instance;
    }

    public List<Recipe> getRecipes() {

        ArrayList<Recipe> recipeArrayList = new ArrayList<>();

        File folder = new File("./recipes");
        File[] listOfFiles = folder.listFiles();

        Gson gson = new Gson();

        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                try {
                    Scanner myReader = new Scanner(file);
                    StringBuilder data = new StringBuilder();
                    while (myReader.hasNextLine()) {
                        String line = myReader.nextLine();
                        data.append(line.strip());
                    }
                    Recipe recipe = gson.fromJson(data.toString(),Recipe.class);
                    recipeArrayList.add(recipe);
                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("File " + file.getName() + "not found.");
                    e.printStackTrace();
                }
            }
        }
        return recipeArrayList;
    }

    public List<String> getRecipeNames() {
        List<Recipe> recipeList = getRecipes();
        ArrayList<String> recipeNames = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            recipeNames.add(recipe.name);
        }
        return recipeNames;
    }

    private List<String> tokenize(String input, String regex) {
        String[] tokens = input.strip().split(regex);
        ArrayList<String> res = new ArrayList<>();
        for (String token : tokens) {
            if (!token.strip().isBlank()) {
                res.add(token.strip());
            }
        }
        return res;
    }

    public void createRecipe(String name, String desc, String ingStr, String insStr, String time, String tagStr) {




        // Convert instructions string to instructions arraylist
        List<String> insTokens = tokenize(insStr,"\n");
        System.out.println(insTokens);
        ArrayList<Instruction> instructions = new ArrayList<>();

        for (String token: insTokens) {
            instructions.add(new Instruction(token,null));
        }




        List<String> tags = tokenize(tagStr,",");
        System.out.println(tags);
    }

}
