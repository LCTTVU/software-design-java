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
    protected final Stage stage;
    protected final String recipeName;
    protected Recipe recipe;

    @FXML
    protected Label title;
    @FXML
    protected Button backButton;
    @FXML
    protected Button doneButton;
    @FXML
    protected TextField nameField;
    @FXML
    protected TextField descField;
    @FXML
    protected TextArea ingArea;
    @FXML
    protected TextArea insArea;
    @FXML
    protected TextField timeField;
    @FXML
    protected TextField tagField;


    public ControllerCreate(String name) {
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
        doneButton.setOnAction(event -> saveRecipe());
        backButton.setOnAction(event -> prevScreen());

        if (recipeName == null) {
            title.setText("Create Recipe");
            return;
        } //stop if creating recipe

        //continue if editing recipe
        title.setText("Edit Recipe");
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


        timeField.setText(recipe.time.toString());
        String tagTxt = recipe.tags.toString();
        tagTxt = tagTxt.substring(1,tagTxt.length() - 1);
        tagField.setText(tagTxt);
    }

    protected void saveRecipe() {
        create();
    }

    protected void create() {
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
        } catch (IndexOutOfBoundsException e) {
            title.setText("Invalid Ingredient Format");
        } catch (Exception e) {
            title.setText(e.getMessage());
        }
    }

    protected void prevScreen() {
        ControllerHome homeController = new ControllerHome();
        homeController.showStage();
        stage.close();
    }
}
