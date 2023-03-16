package softwaredesign;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;




public class Main extends Application {

    public static Frame currFrame;

    public static void main (String[] args) {
        System.out.println("Welcome to Software Design");

        launch(args);

        //currFrame = GUI.getInstance().currFrame;
        RecipeList recipeList = RecipeList.getInstance();
        ArrayList<Recipe> recipes = recipeList.getRecipes();
        for (Recipe recipe : recipes) {
            System.out.println(recipe);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Application");
        stage.setWidth(1280);
        stage.setHeight(720);
        stage.setResizable(false);
        stage.show();

    }

}
