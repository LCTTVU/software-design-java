package softwaredesign;

public class ControllerEdit extends ControllerCreate {

    public ControllerEdit(String name) {
        super(name);
        stage.setTitle("Edit Recipe");
    }

    public void delete() {
        RecipeList.getInstance().deleteRecipe(recipeName);
    }

    @Override
    protected void saveRecipe() {
        delete();
        create();
    }


}
