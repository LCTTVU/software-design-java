package softwaredesign;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCreate implements Initializable {
    private final Stage stage;

    @FXML
    private Label title;
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


    public ControllerCreate() {
        stage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("CreateRecipeScreen.fxml"));
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
        doneButton.setOnAction(event -> createRecipe());
    }

    private void createRecipe() {
        String name = nameField.getText().strip();
        String desc = descField.getText().strip();
        String ingStr = ingArea.getText().strip();
        String insStr = insArea.getText().strip();
        String time = timeField.getText().strip();
        String tagStr = tagField.getText().strip();
        try {
            RecipeList.getInstance().createRecipe(name,desc,ingStr,insStr,time,tagStr);
            ControllerHome homeController = new ControllerHome();
            homeController.showStage();
            stage.close();
        } catch (IllegalArgumentException e) {
            title.setText(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            title.setText("Invalid Ingredient Format");
        }
    }
}
