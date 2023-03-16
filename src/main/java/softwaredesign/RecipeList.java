package softwaredesign;


import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



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
}
