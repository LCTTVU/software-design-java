package softwaredesign;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    private Stage stage;

    @FXML
    private Button createRecipeButton;

    @FXML
    private ListView<String> recipeList;

    public HomeController() {
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
        recipeList.getItems().addAll(RecipeList.getInstance().getRecipeNames());
    }

    public void openCreateRecipeScreen() {
        CreateRecipeController crController = new CreateRecipeController();
        crController.showStage();
        stage.close();

    }
}
