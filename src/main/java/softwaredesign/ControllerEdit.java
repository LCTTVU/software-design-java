package softwaredesign;

import java.io.IOException;

public class ControllerEdit extends ControllerCreate {

    public ControllerEdit(String name) {
        super(name);
        stage.setTitle("Edit Recipe");
    }

    public void delete() {
        try {
            RecipeList.getInstance().deleteRecipe(recipeName);
        } catch (IOException e) {
            title.setText(e.getMessage());
        }
    }

    @Override
    protected void saveRecipe() {
        delete();
        create();
    }

    @Override
    protected void prevScreen() {
        ControllerView viewController = new ControllerView(recipeName);
        viewController.showStage();
        stage.close();
    }
}
