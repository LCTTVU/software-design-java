package softwaredesign;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateRecipeController implements Initializable {
    private Stage stage;

    @FXML
    private Button doneButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField descField;
    @FXML
    private TextArea ingArea;
    @FXML
    private TextArea insArea;
    @FXML
    private TextField timeField;
    @FXML
    private TextField tagField;


    public CreateRecipeController() {
        stage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("CreateRecipeScreen.fxml"));
            loader.setController(this);

            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Create Recipe");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        doneButton.setOnAction(event -> createRecipe());
    }

    public void createRecipe() {
        HomeController homeController = new HomeController();
        homeController.showStage();
        stage.close();

    }
}
