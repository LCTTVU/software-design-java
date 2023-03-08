package softwaredesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {

    public static Frame currFrame;
    public static String appFont = "Arial";

    public static void main (String[] args) {
        System.out.println("Welcome to Software Design");

        currFrame = mkHomeScreen();
    }


    static Frame mkHomeScreen() {

        JLabel title = new JLabel("HOME");
        title.setFont(new Font(appFont,Font.BOLD,40));

        Panel titlePanel = new Panel(Color.red);
        titlePanel.setLayout(new GridBagLayout());
        titlePanel.add(title);

        Panel recipePanel = new Panel(Color.blue);
        Button viewRecipeButton = new BottomButton(Color.green,"View Recipe");
        viewRecipeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currFrame = mkViewRecipeScreen();
            }
        });
        recipePanel.add(viewRecipeButton);

        Panel createRecipePanel = new Panel(Color.gray);
        createRecipePanel.setLayout(new GridBagLayout());

        Button createRecipeButton = new BottomButton(Color.green,"Create Recipe");
        createRecipeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currFrame = mkCreateRecipeScreen();
            }
        });
        createRecipePanel.add(createRecipeButton);

        Frame homeScreen = new Frame("Home Screen");
        homeScreen.setLayout(new BorderLayout());

        homeScreen.add(titlePanel,BorderLayout.NORTH);
        homeScreen.add(recipePanel,BorderLayout.CENTER);
        homeScreen.add(createRecipePanel,BorderLayout.SOUTH);

        return homeScreen;
    }


    static Frame mkCreateRecipeScreen() {

        JLabel label = new JLabel("CREATE RECIPE");
        label.setFont(new Font(appFont,Font.BOLD,40));

        Panel titlePanel = new Panel(Color.red);
        titlePanel.setLayout(new GridBagLayout());
        titlePanel.add(label);

        //Panel crPanel = new

        Panel donePanel = new Panel(Color.gray);
        donePanel.setLayout(new GridBagLayout());

        Button doneButton = new BottomButton(Color.green,"Done");
        doneButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currFrame = mkHomeScreen();
            }
        });

        donePanel.add(doneButton);

        Frame createRecipeScreen = new Frame("Create Recipe");
        createRecipeScreen.setLayout(new BorderLayout());
        createRecipeScreen.add(titlePanel,BorderLayout.NORTH);
        createRecipeScreen.add(donePanel,BorderLayout.SOUTH);

        return createRecipeScreen;
    }

    static Frame mkViewRecipeScreen() {

        JLabel label = new JLabel("RECIPE NAME");
        label.setFont(new Font(appFont,Font.BOLD,40));

        Panel titlePanel = new Panel(Color.red);
        titlePanel.setLayout(new GridBagLayout());
        titlePanel.add(label);

        Panel actionsPanel = new Panel(Color.blue);
        actionsPanel.setLayout(new GridLayout(3,1));

        Button executeButton = new SideButton(Color.green,"Execute");
        executeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currFrame = mkExecuteScreen();
            }
        });

        Button editButton = new SideButton(Color.yellow,"Edit");
        editButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currFrame = mkEditScreen();
            }
        });

        Button deleteButton = new SideButton(Color.red,"Delete");
        deleteButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currFrame = mkHomeScreen();
            }
        });

        actionsPanel.add(executeButton);
        actionsPanel.add(editButton);
        actionsPanel.add(deleteButton);

        Frame viewRecipeScreen = new Frame("View Recipe");
        viewRecipeScreen.setLayout(new BorderLayout());
        viewRecipeScreen.add(titlePanel,BorderLayout.NORTH);
        viewRecipeScreen.add(actionsPanel,BorderLayout.EAST);

        return viewRecipeScreen;
    }

    static Frame mkExecuteScreen() {
        Frame executeScreen = new Frame("Execute Recipe");
        return executeScreen;
    }

    static Frame mkEditScreen() {
        Frame editScreen = new Frame("Edit Recipe");
        return editScreen;
    }
}
