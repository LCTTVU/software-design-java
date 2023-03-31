package softwaredesign;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerEdit extends ControllerCreate {

    public ControllerEdit(File recipePath) {
        super(recipePath);
        this.nextScreen = VIEW;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(event -> controllerFactory(VIEW));
        title.setText(EDIT);
        doneButton.setOnAction(event -> saveRecipe());
        //populate text fields with recipe information for the user to edit
        fillRecipeDetails();
    }

    private void fillRecipeDetails() {
        nameTextField.setText(recipe.getName());

        descTextField.setText(recipe.getDescription());

        StringBuilder ingTxt = new StringBuilder();
        for (Ingredient ingredient : recipe.getIngredients()) {
            ingTxt.append(ingredient.toString());
        }
        ingrTextArea.setText(ingTxt.toString());

        StringBuilder insTxt = new StringBuilder();
        for (Instruction instruction : recipe.getInstructions()) {
            String i = instruction.getText() + "\n";
            insTxt.append(i);
        }
        instTextArea.setText(insTxt.toString());

        timeTextField.setText(recipe.getTime().toString());
        String tagTxt = recipe.getTags().toString();

        tagTxt = tagTxt.substring(1,tagTxt.length() - 1);
        tagsTextField.setText(tagTxt);
    }
}
