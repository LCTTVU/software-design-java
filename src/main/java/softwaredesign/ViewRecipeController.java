package softwaredesign;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewRecipeController implements Initializable {

    private final Stage stage;
    private final String recipeName;

    @FXML
    private Label title;
    @FXML
    private Button editButton;
    @FXML
    private Button executeButton;
    @FXML
    private Button deleteButton;

    public ViewRecipeController(String name) {
        stage = new Stage();
        recipeName = name;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ViewRecipeScreen.fxml"));
            loader.setController(this);

            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Create Recipe");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title.setText(recipeName);
        editButton.setOnAction(event -> editRecipe());
        executeButton.setOnAction(event -> executeRecipe());
        deleteButton.setOnAction(event -> deleteRecipe());

    }

    private void editRecipe() {
        EditRecipeController erController = new EditRecipeController(recipeName);
        erController.showStage();
        stage.close();
    }

    private void executeRecipe() {

    }

    private void deleteRecipe() {
        RecipeList.getInstance().deleteRecipe(recipeName);
        HomeController homeController = new HomeController();
        homeController.showStage();
        stage.close();
    }

}
