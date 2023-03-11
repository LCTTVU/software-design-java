package softwaredesign;

public class RecipeList {
    private static RecipeList instance;
    private RecipeList() {}

    public static RecipeList getRecipes() {
        if (instance == null) {
            instance = new RecipeList();
        }
        return instance;
    }
}
