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
    private Button createRecipeButton;
    @FXML
    private ListView<String> recipeListView;

    public HomeController() {
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

class ViewController extends Controller {

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
        title.setText(recipe.name);
        editButton.setOnAction(event -> controllerFactory(EDIT));
        executeButton.setOnAction(event -> controllerFactory(EXECUTE));
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
            controllerFactory(HOME);
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
        super(CREATE,null);
    }

    //constructor for edit recipe
    public CreateController(File recipePath) {
        super(EDIT,recipePath);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(event -> controllerFactory(HOME));
        title.setText(CREATE);
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
            controllerFactory(nextScreen);
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
    private Label instructionLabel;
    @FXML
    private TextArea annotationArea;
    @FXML
    private Button nextButton;
    @FXML
    private Button prevButton;

    private List<Instruction> newInstructions;
    private int currInstructionIndex;

    public ExecuteController(File recipePath) {
        super(EXECUTE,recipePath);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnAction(event -> controllerFactory(VIEW));
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

    private boolean inBounds(int index) {
        return (index < newInstructions.size() && index >= 0);
    }

    private boolean isLast() {
        return currInstructionIndex == newInstructions.size() - 1;
    }

    private void displayInstruction(int index) {
        Instruction instruction = newInstructions.get(index);
        instructionLabel.setText(instruction.text);
        instructionLabel.setWrapText(true);
        annotationArea.setText(instruction.annotation);
    }

    private void updateInstructions() {
        recipe.updateAnnotation(newInstructions);
        recipe.writeToFile(recipePath);
    }

    private void nextInstruction() {
        saveAnnotation();
        annotationArea.clear();
        currInstructionIndex++;
        if (inBounds(currInstructionIndex)) {
            displayInstruction(currInstructionIndex);
            if (isLast()) nextButton.setText("Finish");
        }
        else {
            updateInstructions();
            controllerFactory(VIEW);
        }
    }

    private void prevInstruction() {
        saveAnnotation();
        annotationArea.clear();
        currInstructionIndex--;
        if (inBounds(currInstructionIndex)) {
            displayInstruction(currInstructionIndex);
            if (!isLast()) nextButton.setText("Next");
        }
        else {
            updateInstructions();
            controllerFactory(VIEW);
        }
    }
}


abstract class Controller implements Initializable {

    protected static final String HOME = "Home";
    protected static final String VIEW = "View Recipe";
    protected static final String CREATE = "Create Recipe";
    protected static final String EDIT = "Edit Recipe";
    protected static final String EXECUTE = "Execute Recipe";

    protected File recipePath;
    protected Recipe recipe;

    protected final Stage stage;

    //Common components
    @FXML
    protected Label title;
    @FXML
    protected Button backButton;

    protected Controller(String name,File path) {
        this.recipePath = path;
        this.recipe = RecipeList.getInstance().getRecipes().get(path);
        this.stage = new Stage();
        this.stage.setScene(sceneFactory(name));
        this.stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        throw new IllegalStateException("Cannot init abstract Controller class");
    }

    private FXMLLoader loadResource(String name) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(name));
        loader.setController(this);
        return loader;
    }

    private Scene sceneFactory(String name) {
        Scene scene = null;
        try {
            switch (name) {
                case HOME:
                    scene = new Scene(loadResource("ScreenHome.fxml").load());
                    break;
                case VIEW:
                    scene = new Scene(loadResource("ScreenView.fxml").load());
                    break;
                case CREATE:
                case EDIT:
                    scene = new Scene(loadResource("ScreenCreateAndEdit.fxml").load());
                    break;
                case EXECUTE:
                    scene = new Scene(loadResource("ScreenExecute.fxml").load());
                    break;
                default:
                    throw new IllegalArgumentException("Invalid Screen at nextScreen");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scene;
    }

    public void controllerFactory(String name) {
        switch (name) {
            case HOME:
                new HomeController();
                break;
            case VIEW:
                new ViewController(recipePath);
                break;
            case CREATE:
                new CreateController();
                break;
            case EDIT:
                new EditController(recipePath);
                break;
            case EXECUTE:
                new ExecuteController(recipePath);
                break;
            default:
                throw new IllegalArgumentException("Invalid Screen at screenFactory");
        }
        this.stage.close();
    }
}
