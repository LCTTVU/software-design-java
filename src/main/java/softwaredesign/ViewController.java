package softwaredesign;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewController extends Controller {

    @FXML
    private Label descText;
    @FXML
    private Label timeText;
    @FXML
    private Label tagsText;
    @FXML
    private Label ingrText;
    @FXML
    private Label instText;
    @FXML
    private Button editButton;
    @FXML
    private Button executeButton;
    @FXML
    private Button deleteButton;

    public ViewController(File recipePath) {
        super(VIEW,recipePath);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(event -> controllerFactory(HOME));
        title.setText(recipe.getName());
        editButton.setOnAction(event -> controllerFactory(EDIT));
        executeButton.setOnAction(event -> controllerFactory(CHECK));
        deleteButton.setOnAction(event -> deleteRecipe());
        fillRecipeDetails();
    }

    private void fillRecipeDetails() {
        descText.setText(recipe.getDescription());
        timeText.setText(recipe.getTime() + " minutes");

        String tagsStr = recipe.getTags().toString();
        tagsStr = tagsStr.substring(1,tagsStr.length() - 1);
        tagsText.setText(tagsStr);
        tagsText.setWrapText(true);

        StringBuilder ingTxt = new StringBuilder();
        for (Ingredient ingredient : recipe.getIngredients()) {
            ingTxt.append("- ").append(ingredient.toString());
        }
        ingrText.setText(ingTxt.toString());
        ingrText.setWrapText(true);

        StringBuilder insTxt = new StringBuilder();
        int step = 1;
        for (Instruction instruction : recipe.getInstructions()) {
            String i = "- Step " + step + ": " + instruction.toString();
            insTxt.append(i);
            step++;
        }
        instText.setText(insTxt.toString());
        instText.setWrapText(true);
    }

    private void deleteRecipe() {
        try {
            RecipeList.deleteRecipe(recipePath);
            controllerFactory(HOME);
        } catch (IOException e) {
            title.setText(e.getMessage());
        }
    }
}
