package softwaredesign;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerExecute implements Initializable {
    private final Stage stage;
    private final String recipeName;

    @FXML
    private Label step;
    @FXML
    private Label instructionLabel;
    @FXML
    private TextArea noteArea;
    @FXML
    private Button backButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button prevButton;

    ControllerExecute(String name) {
        stage = new Stage();
        recipeName = name;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ScreenExecute.fxml"));
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
        backButton.setOnAction(event -> prevScreen());
        nextButton.setOnAction(event -> next());
        prevButton.setOnAction(event -> prev());
    }

    private void next() {

    }

    private void prev() {

    }

    private void prevScreen() {
        ControllerView viewController = new ControllerView(recipeName);
        viewController.showStage();
        stage.close();
    }
}
