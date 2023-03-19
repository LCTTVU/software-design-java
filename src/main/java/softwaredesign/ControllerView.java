package softwaredesign;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerView implements Initializable {

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

    public ControllerView(String name) {
        stage = new Stage();
        recipeName = name;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ScreenView.fxml"));
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
        ControllerCreateAndEdit ceController = new ControllerCreateAndEdit(recipeName);
        ceController.showStage();
        stage.close();
    }

    private void executeRecipe() {

    }

    private void deleteRecipe() {
        try {
            RecipeList.getInstance().deleteRecipe(recipeName);
            ControllerHome homeController = new ControllerHome();
            homeController.showStage();
            stage.close();
        } catch (IOException e) {
            title.setText(e.getMessage());
        }

    }

}
