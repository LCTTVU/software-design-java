package softwaredesign;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static Frame currFrame;

    public static void main (String[] args) {
        System.out.println("Welcome to Software Design");

        //currFrame = GUI.getInstance().currFrame;
        RecipeList recipeList = RecipeList.getInstance();
        ArrayList<Recipe> recipes = recipeList.getRecipes();
        for (Recipe recipe : recipes) {
            System.out.println(recipe);
        }
    }

}
