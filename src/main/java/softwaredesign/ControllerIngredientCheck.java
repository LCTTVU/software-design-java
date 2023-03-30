package softwaredesign;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerIngredientCheck extends Controller {

    @FXML
    private ListView<String> ingredientsListView;

    public ControllerIngredientCheck(File recipePath) {
        super(CHECK,recipePath);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(event -> controllerFactory(VIEW));
        title.setText(recipe.getName() + " ingredients");

        ingredientsListView.getItems().addAll(recipe.getIngredientsString());
        ingredientsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ingredientsListView.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, arg1, arg2) -> {
                    List<String> selectedItems = ingredientsListView.getSelectionModel().getSelectedItems();
                    if (selectedItems.size() == recipe.getIngredients().size()) {
                        controllerFactory(EXECUTE);
                    }
                }
        );
    }

}
