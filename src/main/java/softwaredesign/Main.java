package softwaredesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {

    public static Frame currFrame;
    public static String appFont = "Arial";

    public static void main (String[] args) {
        System.out.println("Welcome to Software Design");

        currFrame = createHomeScreen();
    }


    static Frame createHomeScreen() {

        JLabel title = new JLabel("HOME");
        title.setFont(new Font(appFont,Font.BOLD,40));

        JPanel titlePanel = new Panel(Color.red, new Dimension(100,100));
        titlePanel.setLayout(new GridBagLayout());
        titlePanel.add(title);

        JPanel recipePanel = new Panel(Color.blue, new Dimension(400,100));



        JPanel crPanel = new Panel(Color.gray, new Dimension(100,100));
        crPanel.setLayout(new GridBagLayout());

        JButton crButton = new Button(Color.green,"Create Recipe", new Dimension(250,75));
        crButton.setFont(new Font(appFont,Font.PLAIN,25));
        crButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currFrame = createCrScreen();
            }
        });
        crPanel.add(crButton);

        Frame homeScreen = new Frame("Home Screen");
        homeScreen.setLayout(new BorderLayout());

        homeScreen.add(titlePanel,BorderLayout.NORTH);
        homeScreen.add(recipePanel,BorderLayout.CENTER);
        homeScreen.add(crPanel,BorderLayout.SOUTH);

        return homeScreen;
    }

    static Frame createCrScreen() {

        JLabel label = new JLabel("CREATE RECIPE");
        label.setFont(new Font(appFont,Font.BOLD,40));

        JPanel titlePanel = new Panel(Color.red, new Dimension(100,100));
        titlePanel.setLayout(new GridBagLayout());
        titlePanel.add(label);

        Frame crScreen = new Frame("Create Recipe");
        crScreen.setLayout(new BorderLayout());
        crScreen.add(titlePanel,BorderLayout.NORTH);

        return crScreen;
    }

}
