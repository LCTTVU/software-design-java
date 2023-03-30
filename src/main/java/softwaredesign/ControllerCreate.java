package softwaredesign;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCreate extends Controller {

    protected String nextScreen = HOME;

    @FXML
    protected Button doneButton;
    @FXML
    protected TextField nameTextField;
    @FXML
    protected TextField descTextField;
    @FXML
    protected TextField timeTextField;
    @FXML
    protected TextField tagsTextField;
    @FXML
    protected TextArea ingrTextArea;
    @FXML
    protected TextArea instTextArea;


    public ControllerCreate() {
        super(CREATE,null);
    }

    //constructor for edit recipe
    public ControllerCreate(File recipePath) {
        super(EDIT,recipePath);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(event -> controllerFactory(HOME));
        title.setText(CREATE);
        doneButton.setOnAction(event -> saveRecipe());
    }

    protected void saveRecipe() {
        String name = nameTextField.getText();
        String desc = descTextField.getText();
        String time = timeTextField.getText();
        String tagStr = tagsTextField.getText();
        String ingStr = ingrTextArea.getText();
        String insStr = instTextArea.getText();
        try {
            RecipeList.saveRecipe(recipePath,name,desc,time,tagStr,ingStr,insStr);
            controllerFactory(nextScreen);
        } catch (IndexOutOfBoundsException e) {
            title.setText("Invalid Ingredient Format");
            e.printStackTrace();
        } catch (Exception e) {
            title.setText(e.getMessage());
            e.printStackTrace();
        }
    }

}
