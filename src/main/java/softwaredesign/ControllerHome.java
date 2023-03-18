package softwaredesign;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerHome implements Initializable {
    private Stage stage;

    @FXML
    private Button createRecipeButton;
    @FXML
    private ListView<String> recipeList;

    public ControllerHome() {
        stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HomeScreen.fxml"));
            loader.setController(this);

            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Home");

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createRecipeButton.setOnAction(event -> openCreateRecipeScreen());
        recipeList.getItems().addAll(RecipeList.getInstance().getRecipeNames());
        recipeList.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            String recipeName = recipeList.getSelectionModel().getSelectedItem();
            openViewRecipeScreen(recipeName);
        });
    }

    public void openViewRecipeScreen(String name) {
        ControllerView vrController = new ControllerView(name);
        vrController.showStage();
        stage.close();
    }

    public void openCreateRecipeScreen() {
        ControllerCreate crController = new ControllerCreate(null);
        crController.showStage();
        stage.close();

    }
}
