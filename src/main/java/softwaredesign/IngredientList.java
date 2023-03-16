package softwaredesign;

import java.util.ArrayList;

public class IngredientList {
    private ArrayList<Ingredient> ingredients;

    public IngredientList() {
        ingredients = new ArrayList<>();
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        for(int i = 0; i < ingredients.size(); i++) {
            if(ingredients.get(i).equals(ingredient)) {
                ingredients.remove(i);
            }
        }
    }

   /* public void printIngredients() {
        for(Ingredient i : ingredients) {
            System.out.println(i.toString());
        }
    }*/

}
