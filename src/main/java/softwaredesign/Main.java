package softwaredesign;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main (String[] args){
        System.out.println("Welcome to Software Design");

        JLabel label = new JLabel("App Title");

        JButton createRecipeButton = new Button(Color.green,"Create Recipe");
        createRecipeButton.setBounds(200,200,150,75);


        Frame newFrame = new Frame();
        newFrame.add(label);
        newFrame.add(createRecipeButton);
    }
}
