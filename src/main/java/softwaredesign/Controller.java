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

    @FXML
    protected Button createRecipeButton;
    @FXML
    protected ListView<String> recipeListView;

    public HomeController() {
        super("ScreenHome.fxml",null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(event -> mkPrevScreen(HOME));
        createRecipeButton.setOnAction(event -> mkNextScreen(CREATE_RECIPE));
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
                    mkNextScreen(VIEW_RECIPE);
                }
        );
    }
}

class ViewController extends Controller {

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

    public ViewController(File recipePath) {
        super("ScreenView.fxml",recipePath);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(event -> mkPrevScreen(HOME));
        title.setText(recipe.name);
        editButton.setOnAction(event -> mkNextScreen(EDIT_RECIPE));
        executeButton.setOnAction(event -> mkNextScreen(EXECUTE_RECIPE));
        deleteButton.setOnAction(event -> deleteRecipe());
        fillRecipeDetails();
    }

    private void fillRecipeDetails() {
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
            mkNextScreen(HOME);
        } catch (IOException e) {
            title.setText(e.getMessage());
        }
    }
}

class CreateController extends Controller {

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


    public CreateController() {
        super("ScreenCreateAndEdit.fxml",null);
    }

    //constructor for edit recipe
    public CreateController(File recipePath) {
        super("ScreenCreateAndEdit.fxml",recipePath);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(event -> mkPrevScreen(HOME));
        title.setText(CREATE_RECIPE);
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
            mkNextScreen(nextScreen);
        } catch (IndexOutOfBoundsException e) {
            title.setText("Invalid Ingredient Format");
        } catch (Exception e) {
            title.setText(e.getMessage());
        }
    }

}

class EditController extends CreateController {

    public EditController(File recipePath) {
        super(recipePath);
        this.nextScreen = VIEW_RECIPE;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(event -> mkPrevScreen(VIEW_RECIPE));
        title.setText(EDIT_RECIPE);
        doneButton.setOnAction(event -> saveRecipe());
        //populate text fields with recipe information for the user to edit
        fillRecipeDetails();
    }

    private void fillRecipeDetails() {
        nameTextField.setText(recipe.name);

        descTextField.setText(recipe.description);

        StringBuilder ingTxt = new StringBuilder();
        for (Ingredient ingredient : recipe.ingredients) {
            ingTxt.append(ingredient.toString());
        }
        ingrTextArea.setText(ingTxt.toString());

        StringBuilder insTxt = new StringBuilder();
        for (Instruction instruction : recipe.instructions) {
            String i = instruction.text + "\n";
            insTxt.append(i);
        }
        instTextArea.setText(insTxt.toString());

        timeTextField.setText(recipe.time.toString());
        String tagTxt = recipe.tags.toString();

        tagTxt = tagTxt.substring(1,tagTxt.length() - 1);
        tagsTextField.setText(tagTxt);
    }
}

class ExecuteController extends Controller {

    @FXML
    protected Label instructionLabel;
    @FXML
    protected TextArea annotationArea;
    @FXML
    protected Button nextButton;
    @FXML
    protected Button prevButton;

    private List<Instruction> newInstructions;
    private int currInstructionIndex;

    public ExecuteController(File recipePath) {
        super("ScreenExecute.fxml",recipePath);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(event -> mkPrevScreen(VIEW_RECIPE));
        title.setText(recipe.name);
        nextButton.setOnAction(event -> nextInstruction());
        prevButton.setOnAction(event -> prevInstruction());

        newInstructions = recipe.instructions;
        //show first instruction right away
        currInstructionIndex = 0;
        displayInstruction(currInstructionIndex);
    }

    private void saveAnnotation() {
        newInstructions.get(currInstructionIndex).annotation = annotationArea.getText();
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
        saveAnnotation();
        annotationArea.clear();
        currInstructionIndex++;
        if (inBounds(currInstructionIndex)) {
            displayInstruction(currInstructionIndex);
            if (isLast()) nextButton.setText("Finish");
        }
        else updateInstructions();
    }

    private void prevInstruction() {
        saveAnnotation();
        annotationArea.clear();
        currInstructionIndex--;
        if (inBounds(currInstructionIndex)) {
            displayInstruction(currInstructionIndex);
            if (!isLast()) nextButton.setText("Next");
        }
        else updateInstructions();
    }
}


abstract class Controller implements Initializable {

    protected static final String HOME = "Home";
    protected static final String VIEW_RECIPE = "View Recipe";
    protected static final String CREATE_RECIPE = "Create Recipe";
    protected static final String EDIT_RECIPE = "Edit Recipe";
    protected static final String EXECUTE_RECIPE = "Execute Recipe";

    protected File recipePath;
    protected Recipe recipe;

    protected final Stage stage;

    //Common components
    @FXML
    protected Label title;
    @FXML
    protected Button backButton;

    protected Controller(String resource,File path) {
        this.recipePath = path;
        this.recipe = RecipeList.getInstance().getRecipes().get(path);
        this.stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(resource));
            loader.setController(this);
            stage.setScene(new Scene(loader.load()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        throw new IllegalStateException("Cannot init abstract Controller class");
    }

    protected void mkPrevScreen(String prev) {
        Controller prevController;
        switch (prev) {
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

    protected void mkNextScreen(String next) {
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
}
