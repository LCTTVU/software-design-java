package softwaredesign;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;




public class Main extends Application {

    public static Frame currFrame;

    public static void main (String[] args) {
        System.out.println("Welcome to Software Design");



        //currFrame = GUI.getInstance().currFrame;
        /*
        RecipeList recipeList = RecipeList.getInstance();
        ArrayList<Recipe> recipes = recipeList.getRecipes();
        for (Recipe recipe : recipes) {
            System.out.println(recipe);
        }
        */
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("HomeScreen.fxml"));
        stage.setTitle("Application");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();

    }

}
