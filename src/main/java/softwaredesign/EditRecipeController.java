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

public class EditRecipeController implements Initializable {
    private final Stage stage;
    private final String recipeName;

    private Recipe recipe;


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


    public EditRecipeController(String name) {
        stage = new Stage();
        recipeName = name;
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

        doneButton.setOnAction(event -> editRecipe());

        recipe = RecipeList.getInstance().getRecipe(recipeName);

        String nameTxt = recipe.name;
        nameField.setText(nameTxt);

        String descTxt = recipe.description;
        descField.setText(descTxt);

        StringBuilder ingTxt = new StringBuilder();
        for (Ingredient ingredient : recipe.ingredients) {
            ingTxt.append(ingredient.toString());
        }
        ingArea.setText(ingTxt.toString());

        StringBuilder insTxt = new StringBuilder();
        for (Instruction instruction : recipe.instructions) {
           insTxt.append(instruction.toString());
        }
        insArea.setText(insTxt.toString());




        String timeTxt = recipe.time;
        timeField.setText(timeTxt);
        String tagTxt = recipe.tags.toString();
        tagTxt = tagTxt.substring(1,tagTxt.length() - 1);
        tagField.setText(tagTxt);

    }

    public void delete() {
        RecipeList.getInstance().deleteRecipe(recipeName);
    }

    public void create() {
        String name = nameField.getText().strip();
        String desc = descField.getText().strip();
        String ingStr = ingArea.getText().strip();
        String insStr = insArea.getText().strip();
        String time = timeField.getText().strip();
        String tagStr = tagField.getText().strip();
        try {
            RecipeList.getInstance().createRecipe(name,desc,ingStr,insStr,time,tagStr);
            HomeController homeController = new HomeController();
            homeController.showStage();
            stage.close();
        } catch (IllegalArgumentException e) {
            title.setText(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            title.setText("Invalid Ingredient Format");
        }
    }

    public void editRecipe() {
        delete();
        create();
    }


}
