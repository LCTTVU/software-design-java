package softwaredesign;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerHome extends Controller {

    @FXML
    private Button createRecipeButton;
    @FXML
    private ListView<String> recipeListView;

    public ControllerHome() {
        super(HOME,null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(event -> controllerFactory(HOME));
        createRecipeButton.setOnAction(event -> controllerFactory(CREATE));
        fillRecipeList();
    }

    private void fillRecipeList() {
        recipeListView.getItems().addAll(RecipeList.getInstance().getRecipeNameList());
        /*
        Add individual event listeners for viewing recipe to each row of recipeList
        (this code was corrected by intellij)
         */
        recipeListView.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, arg1, arg2) -> {
                    String listItem = recipeListView.getSelectionModel().getSelectedItem();
                    recipePath = RecipeList.getInstance().getFilename(listItem);
                    controllerFactory(VIEW);
                }
        );
    }
}
