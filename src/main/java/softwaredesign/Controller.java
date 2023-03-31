package softwaredesign;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


abstract class Controller implements Initializable {

    protected static final String HOME = "Home";
    protected static final String VIEW = "View Recipe";
    protected static final String CREATE = "Create Recipe";
    protected static final String EDIT = "Edit Recipe";
    protected static final String CHECK = "Ingredient Check";
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
                case CHECK:
                    scene = new Scene(loadResource("IngredientCheck.fxml").load());
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
                new ControllerHome();
                break;
            case VIEW:
                new ViewController(recipePath);
                break;
            case CREATE:
                new ControllerCreate();
                break;
            case EDIT:
                new ControllerEdit(recipePath);
                break;
            case CHECK:
                new ControllerIngredientCheck(recipePath);
                break;
            case EXECUTE:
                new ControllerExecute(recipePath);
                break;
            default:
                throw new IllegalArgumentException("Invalid Screen at screenFactory");
        }
        this.stage.close();
    }
}
