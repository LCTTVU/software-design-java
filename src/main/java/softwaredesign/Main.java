package softwaredesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {

    public static Frame currFrame;

    public static void main (String[] args) {
        System.out.println("Welcome to Software Design");

        currFrame = createHomeScreen();
    }


    static Frame createHomeScreen() {

        JButton button = new Button(Color.green,"Create Recipe", new Dimension(150,75));
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currFrame = createCrScreen();
            }
        });

        JPanel crPanel = new Panel(Color.gray,new Dimension(100,100));
        crPanel.setLayout(new GridBagLayout());
        crPanel.add(button);

        Frame homeScreen = new Frame("Home Screen");
        homeScreen.setLayout(new BorderLayout());
        homeScreen.add(crPanel,BorderLayout.SOUTH);

        return homeScreen;
    }

    static Frame createCrScreen() {

        JLabel label = new JLabel();
        label.setText("CREATE RECIPE");
        label.setFont(new Font("Arial",Font.BOLD,25));

        JPanel titlePanel = new Panel(Color.red,new Dimension(100,75));
        titlePanel.setLayout(new GridBagLayout());
        titlePanel.add(label);

        Frame crScreen = new Frame("Create Recipe");
        crScreen.setLayout(new BorderLayout());
        crScreen.add(titlePanel,BorderLayout.NORTH);

        return crScreen;
    }

}
