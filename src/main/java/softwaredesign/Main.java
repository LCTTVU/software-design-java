package softwaredesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


class Panel extends JPanel {
    Panel(Color color){
        this.setBackground(color);
        this.setPreferredSize(new Dimension(100,90));
    }
}

class Frame extends JFrame {
    Frame(String title) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(title);
        this.setSize(1280,720);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);
    }
}

public class Main {

    public static Frame currFrame;
    public static final Font titleFont = new Font("Arial",Font.BOLD,36);
    public static final Font h1Font = new Font("Arial",Font.BOLD,18);
    public static final Font h2Font = new Font("Arial",Font.PLAIN,16);


    public static void main (String[] args) {
        System.out.println("Welcome to Software Design");

        currFrame = mkHomeScreen();
    }


    static Frame mkHomeScreen() {

        JLabel title = new JLabel("HOME");
        title.setFont(titleFont);

        Panel titlePanel = new Panel(Color.red);
        titlePanel.setLayout(new GridBagLayout());
        titlePanel.add(title);

        Panel recipePanel = new Panel(Color.blue);
        Button viewRecipeButton = new BottomButton(Color.green,"View Recipe");
        viewRecipeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currFrame.setVisible(false);
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
                currFrame.setVisible(false);
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
        label.setFont(titleFont);

        Panel titlePanel = new Panel(Color.red);
        titlePanel.setLayout(new GridBagLayout());
        titlePanel.add(label);

        Panel createRecipePanel = new Panel(Color.blue);
        createRecipePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblName = new JLabel("Name");
        lblName.setFont(h1Font);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 20, 0, 0);
        gbc.weightx = 0.1;
        createRecipePanel.add(lblName, gbc);

        JTextField txtName = new JTextField();
        txtName.setFont(h2Font);
        gbc.gridwidth = 3;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 0, 0, 20);
        gbc.weightx = 1;
        createRecipePanel.add(txtName, gbc);

        JLabel lblDescription = new JLabel("Description");
        lblDescription.setFont(h1Font);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 20, 0, 0);
        gbc.weightx = 0.1;
        createRecipePanel.add(lblDescription, gbc);

        JTextField txtDescription = new JTextField();
        txtDescription.setFont(h2Font);
        gbc.gridwidth = 3;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 0, 20);
        gbc.weightx = 1;
        createRecipePanel.add(txtDescription, gbc);

        JLabel lblTags = new JLabel("Tags");
        lblTags.setFont(h1Font);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 20, 0, 0);
        gbc.weightx = 0.1;
        createRecipePanel.add(lblTags, gbc);

        JTextField txtTags = new JTextField();
        txtTags.setFont(h2Font);
        gbc.gridwidth = 3;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.insets = new Insets(5, 0, 0, 20);
        createRecipePanel.add(txtTags, gbc);

        JLabel lblIngredients = new JLabel("Ingredients");
        lblIngredients.setFont(h1Font);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 20, 0, 0);
        gbc.weightx = 0.1;
        createRecipePanel.add(lblIngredients, gbc);

        JTextArea txtAreaIngredients = new JTextArea(10, 20);
        txtAreaIngredients.setFont(h2Font);
        JScrollPane paneIngredients = new JScrollPane(txtAreaIngredients);
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 3;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 0, 0, 20);
        gbc.weightx = 1;
        createRecipePanel.add(paneIngredients, gbc);

        JLabel lblInstructions = new JLabel("Instructions");
        lblInstructions.setFont(h1Font);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 20, 0, 0);
        gbc.weightx = 0.1;
        createRecipePanel.add(lblInstructions, gbc);

        JTextArea txtAreaInstructions = new JTextArea(10, 20);
        txtAreaInstructions.setFont(h2Font);
        JScrollPane paneInstructions = new JScrollPane(txtAreaInstructions);
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 3;
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 0, 0, 20);
        gbc.weightx = 1;
        createRecipePanel.add(paneInstructions, gbc);


        Panel donePanel = new Panel(Color.gray);
        donePanel.setLayout(new GridBagLayout());

        Button doneButton = new BottomButton(Color.green,"Done");
        doneButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currFrame.setVisible(false);
                currFrame = mkHomeScreen();
            }
        });

        donePanel.add(doneButton);

        Frame createRecipeScreen = new Frame("Create Recipe");
        createRecipeScreen.setLayout(new BorderLayout());
        createRecipeScreen.add(titlePanel,BorderLayout.NORTH);
        createRecipeScreen.add(createRecipePanel,BorderLayout.CENTER);
        createRecipeScreen.add(donePanel,BorderLayout.SOUTH);

        return createRecipeScreen;
    }

    static Frame mkViewRecipeScreen() {

        JLabel label = new JLabel("RECIPE NAME");
        label.setFont(titleFont);

        Panel titlePanel = new Panel(Color.red);
        titlePanel.setLayout(new GridBagLayout());
        titlePanel.add(label);

        Panel actionsPanel = new Panel(Color.blue);
        actionsPanel.setLayout(new GridLayout(3,1));

        Button executeButton = new SideButton(Color.green,"Execute");
        executeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currFrame.setVisible(false);
                currFrame = mkExecuteScreen();
            }
        });

        Button editButton = new SideButton(Color.yellow,"Edit");
        editButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currFrame.setVisible(false);
                currFrame = mkEditScreen();
            }
        });

        Button deleteButton = new SideButton(Color.red,"Delete");
        deleteButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currFrame.setVisible(false);
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
