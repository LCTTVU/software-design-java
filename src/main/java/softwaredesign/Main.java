package softwaredesign;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static Frame currFrame;
    public static Recipe newRecipe;

    public static void main (String[] args) {
        System.out.println("Welcome to Software Design");

        //currFrame = GUI.getInstance().currFrame;
        RecipeList recipeList = RecipeList.getInstance();
        ArrayList<Recipe> recipes = recipeList.getRecipes();
        newRecipe = recipeList.createRecipe();
        writeToFile();

    }

    public static void writeToFile() {
        File folder = new File("./recipes/test3.json");
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(folder)) {
            gson.toJson(newRecipe, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
