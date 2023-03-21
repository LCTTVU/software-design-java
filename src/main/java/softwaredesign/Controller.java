package softwaredesign;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;


class HomeController extends Controller {
    public HomeController() {
        super(HOME,"ScreenHome.fxml",null);
    }
}

class ViewController extends Controller {
    public ViewController(File recipePath) {
        super(VIEW_RECIPE,"ScreenView.fxml",recipePath);
    }
}

class CreateController extends Controller {
    public CreateController() {
        super(CREATE_RECIPE,"ScreenCreateAndEdit.fxml",null);
    }
}

class EditController extends Controller {
    public EditController(File recipePath) {
        super(EDIT_RECIPE,"ScreenCreateAndEdit.fxml",recipePath);
    }
}

class ExecuteController extends Controller {
    public ExecuteController(File recipePath) {
        super(EXECUTE_RECIPE,"ScreenExecute.fxml",recipePath);
    }
}


public class Controller implements Initializable {

    protected static final String HOME = "Home";
    protected static final String VIEW_RECIPE = "View Recipe";
    protected static final String CREATE_RECIPE = "Create Recipe";
    protected static final String EDIT_RECIPE = "Edit Recipe";
    protected static final String EXECUTE_RECIPE = "Execute Recipe";

    protected File recipePath;
    protected Recipe recipe;

    protected final Stage stage;
    protected String screenName;

    //Common components
    @FXML
    protected Label title;
    @FXML
    protected Button backButton;
    //Home screen components
    @FXML
    protected Button createRecipeButton;
    @FXML
    protected ListView<String> recipeListView;
    //Create screen and Edit screen components
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
    //View screen components
    @FXML
    protected Label descText;
    @FXML
    protected Label timeText;
    @FXML
    protected Label tagsText;
    @FXML
    protected Label ingrText;
    @FXML
    protected Label instText;
    @FXML
    protected Button editButton;
    @FXML
    protected Button executeButton;
    @FXML
    protected Button deleteButton;
    //Execute screen components
    @FXML
    protected Label instructionLabel;
    @FXML
    protected TextArea annotationArea;
    @FXML
    protected Button nextButton;
    @FXML
    protected Button prevButton;
    //keep a copy of the recipe's instructions to update annotations by the user after execution
    protected List<Instruction> newInstructions;
    protected int currInstructionIndex;

    public Controller(String screen, String resource,File path) {
        screenName = screen;
        recipePath = path;
        recipe = RecipeList.getInstance().getRecipes().get(recipePath);
        stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(resource));
            loader.setController(this);
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(screen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(event -> mkPrevScreen());
        switch (screenName) {
            case HOME:
                createRecipeButton.setOnAction(event -> mkNextScreen(CREATE_RECIPE));
                fillRecipesHome();
                break;

            case VIEW_RECIPE:
                title.setText(recipe.name);
                editButton.setOnAction(event -> mkNextScreen(EDIT_RECIPE));
                executeButton.setOnAction(event -> mkNextScreen(EXECUTE_RECIPE));
                deleteButton.setOnAction(event -> deleteRecipe());
                fillRecipeDetailsView();
                break;

            case CREATE_RECIPE:
                title.setText(CREATE_RECIPE);
                doneButton.setOnAction(event -> saveRecipe());
                break;

            case EDIT_RECIPE:
                title.setText(EDIT_RECIPE);
                doneButton.setOnAction(event -> saveRecipe());
                //populate text fields with recipe information for the user to edit
                fillRecipeDetailsEdit();
                break;

            case EXECUTE_RECIPE:
                title.setText(recipe.name);
                nextButton.setOnAction(event -> nextInstruction());
                prevButton.setOnAction(event -> prevInstruction());

                newInstructions = recipe.instructions;
                //show first instruction right away
                currInstructionIndex = 0;
                displayInstruction(currInstructionIndex);
                break;

            default:
                throw new IllegalArgumentException("Invalid Screen at init");
        }
    }

    private void fillRecipesHome() {
        recipeListView.getItems().addAll(RecipeList.getInstance().getRecipeNameList());
                /*
                Add individual event listeners for viewing recipe to each row of recipeList
                (this code was corrected by intellij)
                 */
        recipeListView.getSelectionModel().selectedItemProperty().addListener(
            (observableValue, arg1, arg2) -> {
                String listItem = recipeListView.getSelectionModel().getSelectedItem();
                recipePath = RecipeList.getInstance().getFilename(listItem);
                mkNextScreen(VIEW_RECIPE);
            }
        );
    }

    private void fillRecipeDetailsView() {
        descText.setText(recipe.description);
        timeText.setText(recipe.time + " minutes");

        String tagsStr = recipe.tags.toString();
        tagsStr = tagsStr.substring(1,tagsStr.length() - 1);
        tagsText.setText(tagsStr);
        tagsText.setWrapText(true);

        StringBuilder ingTxt = new StringBuilder();
        for (Ingredient ingredient : recipe.ingredients) {
            ingTxt.append("- ").append(ingredient.toString());
        }
        ingrText.setText(ingTxt.toString());
        ingrText.setWrapText(true);

        StringBuilder insTxt = new StringBuilder();
        int step = 1;
        for (Instruction instruction : recipe.instructions) {
            String i = "Step " + step + ": " + instruction.text + "\nNote: " + instruction.annotation + "\n";
            insTxt.append(i);
            step++;
        }
        instText.setText(insTxt.toString());
        instText.setWrapText(true);


    }

    private void fillRecipeDetailsEdit() {
        nameTextField.setText(recipe.name);

        descTextField.setText(recipe.description);

        StringBuilder ingTxt = new StringBuilder();
        for (Ingredient ingredient : recipe.ingredients) {
            ingTxt.append(ingredient.toString());
        }
        ingrTextArea.setText(ingTxt.toString());

        StringBuilder insTxt = new StringBuilder();
        for (Instruction instruction : recipe.instructions) {
            insTxt.append(instruction.toString());
        }
        instTextArea.setText(insTxt.toString());

        timeTextField.setText(recipe.time.toString());
        String tagTxt = recipe.tags.toString();

        tagTxt = tagTxt.substring(1,tagTxt.length() - 1);
        tagsTextField.setText(tagTxt);
    }

    private void mkPrevScreen() {
        Controller prevController;
        switch (screenName) {
            case HOME:
            case VIEW_RECIPE:
            case CREATE_RECIPE:
                prevController = new HomeController();  //3 of these screens have home as a common prev
                break;
            case EDIT_RECIPE:
            case EXECUTE_RECIPE:
                prevController = new ViewController(recipePath); //same reason as above
                break;
            default:
                throw new IllegalArgumentException("Invalid Screen at prevScreen");
        }
        prevController.stage.show();
        stage.close();
    }

    private void mkNextScreen(String next) {
        Controller nextController;
        switch (next) {
            case HOME:
                nextController = new HomeController();
                break;
            case VIEW_RECIPE:
                nextController = new ViewController(recipePath);
                break;
            case CREATE_RECIPE:
                nextController = new CreateController();
                break;
            case EDIT_RECIPE:
                nextController = new EditController(recipePath);
                break;
            case EXECUTE_RECIPE:
                nextController = new ExecuteController(recipePath);
                break;
            default:
                throw new IllegalArgumentException("Invalid Screen at nextScreen");
        }
        nextController.stage.show();
        stage.close();
    }

    //this method is used for both creating and editing (editing overwrites the existing recipe)
    private void saveRecipe() {
        String name = nameTextField.getText();
        String desc = descTextField.getText();
        String time = timeTextField.getText();
        String tagStr = tagsTextField.getText();
        String ingStr = ingrTextArea.getText();
        String insStr = instTextArea.getText();
        try {
            RecipeList.saveRecipe(recipePath,name,desc,time,tagStr,ingStr,insStr);
            if (Objects.equals(screenName,CREATE_RECIPE)) mkNextScreen(HOME); //home screen if creating
            else mkNextScreen(VIEW_RECIPE); //back to view screen if editing
        } catch (IndexOutOfBoundsException e) {
            title.setText("Invalid Ingredient Format");
        } catch (Exception e) {
            title.setText(e.getMessage());
        }
    }

    private void deleteRecipe() {
        try {
            RecipeList.deleteRecipe(recipePath);
            mkNextScreen(HOME);
        } catch (IOException e) {
            title.setText(e.getMessage());
        }
    }

    //Execute recipe methods
    private void saveAndClearAnnotation() {
        newInstructions.get(currInstructionIndex).annotation = annotationArea.getText().strip();
        annotationArea.clear();
    }

    private boolean inBounds(int i) {
        return (i < newInstructions.size() && i >= 0);
    }

    private boolean isLast() {
        return currInstructionIndex == newInstructions.size() - 1;
    }

    private void displayInstruction(int i) {
        Instruction instruction = newInstructions.get(i);
        instructionLabel.setText(instruction.text);
        instructionLabel.setWrapText(true);
        annotationArea.setText(instruction.annotation);
    }

    private void updateInstructions() {
        recipe.updateInstructions(newInstructions);
        recipe.writeToFile(recipePath);
        mkNextScreen(VIEW_RECIPE);
    }

    private void nextInstruction() {
        saveAndClearAnnotation();
        currInstructionIndex++;
        if (inBounds(currInstructionIndex)) {
            displayInstruction(currInstructionIndex);
            if (isLast()) nextButton.setText("Finish");
        }
        else updateInstructions();
    }

    private void prevInstruction() {
        saveAndClearAnnotation();
        currInstructionIndex--;
        if (inBounds(currInstructionIndex)) {
            displayInstruction(currInstructionIndex);
            if (!isLast()) nextButton.setText("Next");
        }
        else updateInstructions();
    }
}
