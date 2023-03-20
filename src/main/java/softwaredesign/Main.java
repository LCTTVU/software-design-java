package softwaredesign;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main (String[] args) {
        System.out.println("Welcome to Software Design");
        launch(args);

    }

    @Override
    public void start(Stage stage) {
        HomeController homeController = new HomeController();
        homeController.stage.show();
    }

}
