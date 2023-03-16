package softwaredesign;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToHomeScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("HomeScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToCreateRecipeScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("CreateRecipeScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToViewRecipeScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("ViewRecipeScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToExecuteRecipeScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("ViewRecipeScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
