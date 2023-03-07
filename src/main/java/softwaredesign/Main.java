package softwaredesign;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Main {
    public static void main (String[] args){
        System.out.println("Welcome to Software Design");

        Frame newFrame = new Frame("Home Screen");
        newFrame.setLayout(new BorderLayout());

        JPanel crPanel = new Panel(Color.gray,new Dimension(100,100));

        JButton createRecipeButton = new Button(Color.green,"Create Recipe", new Dimension(150,75));

        crPanel.add(createRecipeButton);
        newFrame.add(crPanel,BorderLayout.SOUTH);
    }
}
