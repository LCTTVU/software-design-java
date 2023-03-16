package softwaredesign;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Objects;

class AppButton extends Button {
    AppButton(String color, String text, Font font) {
        this.setStyle("fx-background-color: " + color);
        this.setText(text);
        this.setFont(font);
    }

    static AppButton createButton(String type, String color, String text) {
        AppButton button;
        if (Objects.equals(type,"side")) {
            button = new AppButton(color, text, GUI_new.h2Font);
            button.setPrefWidth(120);
            button.setPrefHeight(240);
            button.setLayoutX(1160);
        }
        else {
            button = new AppButton(color, text, GUI_new.h1Font);
            button.setPrefWidth(400);
            button.setPrefHeight(100);
            button.setLayoutX(440);
            button.setLayoutY(620);
        }
        return button;
    }
}

public class GUI_new {

    public Scene currScene;

    public static final Font titleFont = new Font("Arial",36);
    public static final Font h1Font = new Font("Arial",25);
    public static final Font h2Font = new Font("Arial",12);


    public static final String green = "#3CB043";
    public static final String red = "#FF0000";
    public static final String yellow = "#FFE900";

    private static GUI_new instance;
    private GUI_new() {
        this.currScene = mkHomeScreen();
    }

    public static GUI_new getInstance() {
        if (instance == null) {
            instance = new GUI_new();
        }
        return instance;
    }


    public Scene mkHomeScreen() {
        Group root = new Group();
        Scene scene = new Scene(root,1280,720, Color.WHITE);


        AppButton createRecipeButton = AppButton.createButton("bottom",green,"Create Recipe");
        root.getChildren().add(createRecipeButton);

        return scene;
    }
}
