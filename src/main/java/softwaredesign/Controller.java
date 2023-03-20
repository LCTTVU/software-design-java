package softwaredesign;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;


class HomeController extends Controller {
    public HomeController() {
        super(HOME,"ScreenHome.fxml",null);
    }
}

class ViewController extends Controller {
    public ViewController(String recipePath) {
        super(VIEW_RECIPE,"ScreenView.fxml",recipePath);
    }
}

class CreateController extends Controller {
    public CreateController() {
        super(CREATE_RECIPE,"ScreenCreateAndEdit.fxml",null);
    }
}

class EditController extends Controller {
    public EditController(String recipePath) {
        super(EDIT_RECIPE,"ScreenCreateAndEdit.fxml",recipePath);
    }
}

class ExecuteController extends Controller {
    public ExecuteController(String recipePath) {
        super(EXECUTE_RECIPE,"ScreenExecute.fxml",recipePath);
    }
}


public class Controller implements Initializable {

    protected static final String HOME = "Home";
    protected static final String VIEW_RECIPE = "View Recipe";
    protected static final String CREATE_RECIPE = "Create Recipe";
    protected static final String EDIT_RECIPE = "Edit Recipe";
    protected static final String EXECUTE_RECIPE = "Execute Recipe";

    protected Map<String,Recipe> recipes;
    protected String recipePath;
    protected Recipe recipe;

    protected final Stage stage;
    protected String screenName;
    protected String resourceName;

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
    //View screen components
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
    protected TextArea noteArea;
    @FXML
    protected Button nextButton;
    @FXML
    protected Button prevButton;
    protected List<Instruction> newInstructions;
    protected int index;

    //common functions
    public Controller(String screen, String resource,String path) {
        screenName = screen;
        resourceName = resource;
        recipePath = path;
        recipes = RecipeList.getRecipes();
        recipe = recipes.get(recipePath);
        stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(resourceName));
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
                recipeListView.getItems().addAll(RecipeList.getRecipeNames(recipes));

                /*
                Add individual event listeners for viewing recipe to each row of recipeList
                (this code was corrected by intellij)
                 */
                recipeListView.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
                    String listItem = recipeListView.getSelectionModel().getSelectedItem();
                    recipePath = RecipeList.getFilenameFromRecipeName(recipes,listItem);
                    mkNextScreen(VIEW_RECIPE);
                });
                break;

            case VIEW_RECIPE:
                title.setText(recipe.name);
                editButton.setOnAction(event -> mkNextScreen(EDIT_RECIPE));
                executeButton.setOnAction(event -> mkNextScreen(EXECUTE_RECIPE));
                deleteButton.setOnAction(event -> deleteRecipe());
                break;

            case CREATE_RECIPE:
                title.setText(CREATE_RECIPE);
                doneButton.setOnAction(event -> saveRecipe());
                break;

            case EDIT_RECIPE:
                title.setText(EDIT_RECIPE);
                doneButton.setOnAction(event -> saveRecipe());
                //populate text fields with recipe information for the user to edit
                nameField.setText(recipe.name);

                descField.setText(recipe.description);

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
                break;

            case EXECUTE_RECIPE:
                title.setText(recipe.name);
                newInstructions = recipe.instructions;
                index = 0;
                Instruction first = newInstructions.get(index);
                instructionLabel.setText(first.text);
                noteArea.setText(first.note);
                nextButton.setOnAction(event -> nextInstruction());
                prevButton.setOnAction(event -> prevInstruction());
                break;

            default:
                throw new IllegalArgumentException("Invalid Screen at init");
        }
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

    //this function is used for both creating and editing (editing overwrites the existing recipe)
    private void saveRecipe() {
        String name = nameField.getText();
        String desc = descField.getText();
        String ingStr = ingArea.getText();
        String insStr = insArea.getText();
        String time = timeField.getText();
        String tagStr = tagField.getText();
        try {
            RecipeList.saveRecipe(recipePath,name,desc,ingStr,insStr,time,tagStr);
            mkNextScreen(HOME);
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

    //Executing recipe functions
    private void updateNote() {
        newInstructions.get(index).note = noteArea.getText().strip();
    }

    private int nrInstructions() {
        return newInstructions.size();
    }

    private boolean isLast() {
        return index == nrInstructions() - 1;
    }

    private void nextInstruction() {
        updateNote();
        noteArea.clear();
        index++;
        if (index < nrInstructions()) {
            Instruction next = newInstructions.get(index);
            instructionLabel.setText(next.text);
            noteArea.setText(next.note);
            if (isLast()) nextButton.setText("Finish");
        }
        else {
            recipe.updateInstructions(recipePath, newInstructions);
            mkNextScreen(VIEW_RECIPE);
        }
    }

    private void prevInstruction() {
        updateNote();
        noteArea.clear();
        index--;
        if (index >= 0) {
            Instruction prev = newInstructions.get(index);
            instructionLabel.setText(prev.text);
            noteArea.setText(prev.note);
            if (!isLast()) nextButton.setText("Next");
        }
        else {
            recipe.updateInstructions(recipePath, newInstructions);
            mkNextScreen(VIEW_RECIPE);
        }
    }
}
