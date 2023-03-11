package softwaredesign;

import java.util.ArrayList;

public class RecipeList {

    ArrayList<Recipe> recipes;

    private static RecipeList instance;
    private RecipeList() {}

    public static RecipeList getRecipes() {
        if (instance == null) {
            instance = new RecipeList();
        }
        return instance;
    }
}
