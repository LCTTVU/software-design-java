package softwaredesign;


import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;



public class RecipeList {

    ArrayList<Recipe> recipes;
    IngredientList ingredientList;

    private static RecipeList instance;
    private RecipeList() {
        this.recipes = getRecipes();
        this.ingredientList = new IngredientList();
    }

    public static RecipeList getInstance() {
        if (instance == null) {
            instance = new RecipeList();
        }

        return instance;
    }

    public ArrayList<Recipe> getRecipes() {

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
        recipes = recipeArrayList;
        return recipeArrayList;
    }

    public Recipe createRecipe() {
        ArrayList<Instruction> instructions = new ArrayList<>();
        ArrayList<String> tags = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the recipe");
        String name = scanner.nextLine();
        System.out.println("Please enter the description");
        String description = scanner.nextLine();
        System.out.println("How many ingredients would you like to add?");
        int count = scanner.nextInt();
        for(int i = 0; i < count; i++) {
            System.out.println("Enter Ingredient Name");
            String ingName = scanner.next();
            System.out.println("Enter quantity (without units)");
            int quantity = scanner.nextInt();
            System.out.println("Enter the units");
            String unit = scanner.next();
            ingredientList.addIngredient(new Ingredient(ingName,quantity,unit));
        }
        System.out.println("How many steps would you like to add?");
        int count1 = scanner.nextInt();
        System.out.println("Enter Instruction");
        for(int i = 0; i < count1; i++) {
            scanner.next();
            String instruction = scanner.nextLine();
            instructions.add(new Instruction(instruction));
        }
        System.out.println("Enter the amount of time needed with units please");
        String time = scanner.nextLine();
        System.out.println("Enter how many tags you need?");
        int count3 = scanner.nextInt();
        System.out.println("Enter tag");
        for(int i = 0; i < count3; i++) {
            String tag = scanner.next();
            tags.add(tag);
        }
        Recipe newRecipe = new Recipe(name, description,ingredientList.getIngredients(),instructions,time, tags);
        return newRecipe;
    }


}
