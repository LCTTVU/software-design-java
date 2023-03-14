package softwaredesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;


class Button extends JButton {
    Button(Color color, String text, Font font){
        this.setText(text);
        this.setBackground(color);
        this.setFont(font);
        this.setFocusable(false);
    }

    static Button createButton(String type, Color color, String text) {
        if (Objects.equals(type, "bottom")) {
            return new Button(color,text,GUI.h1Font);
        }
        else {
            return new Button(color,text,GUI.h2Font);
        }
    }
}

class Panel extends JPanel {
    Panel(Color color){
        this.setBackground(color);
        this.setPreferredSize(new Dimension(90,75));
    }
}

class Frame extends JFrame {
    Frame(String title) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(title);
        this.setSize(1280,720);
        this.setResizable(false);
        this.setLayout(null);
    }
}

public class GUI {

    public Frame currFrame;

    public static final Font titleFont = new Font("Arial",Font.BOLD,36);
    public static final Font h1Font = new Font("Arial",Font.BOLD,25);
    public static final Font h2Font = new Font("Arial",Font.PLAIN,16);

    private static GUI instance;
    private GUI(){
        this.currFrame = mkHomeScreen();
    }

    public static GUI getInstance() {
        if (instance == null) {
            instance = new GUI();
        }
        return instance;
    }

    public Panel mkDonePanel() {
        Panel donePanel = new Panel(Color.gray);
        donePanel.setLayout(new GridLayout());

        Button doneButton = Button.createButton("bottom",Color.gray,"Done");
        doneButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currFrame.setVisible(false);
                currFrame = mkHomeScreen();
            }
        });

        donePanel.add(doneButton);
        return donePanel;
    }

    public Frame mkHomeScreen() {

        JLabel title = new JLabel("HOME");
        title.setFont(titleFont);

        Panel titlePanel = new Panel(Color.red);
        titlePanel.setLayout(new GridBagLayout());
        titlePanel.add(title);

        Panel recipePanel = new Panel(Color.blue);
        Button viewRecipeButton = Button.createButton("bottom",Color.green,"View Recipe");
        viewRecipeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currFrame.setVisible(false);
                currFrame = mkViewRecipeScreen();
            }
        });
        recipePanel.add(viewRecipeButton);

        Panel createRecipePanel = new Panel(Color.gray);
        createRecipePanel.setLayout(new GridLayout());

        Button createRecipeButton = Button.createButton("bottom",Color.gray,"Create Recipe");
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
        homeScreen.setVisible(true);
        return homeScreen;
    }

    public Frame mkCreateRecipeScreen() {

        JLabel label = new JLabel("CREATE RECIPE");
        label.setFont(titleFont);

        Panel titlePanel = new Panel(Color.red);
        titlePanel.setLayout(new GridBagLayout());
        titlePanel.add(label);

        Panel createRecipePanel = new Panel(Color.blue);
        createRecipePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(h1Font);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 20, 0, 0);
        gbc.weightx = 0.1;
        createRecipePanel.add(nameLabel, gbc);

        JTextField nameText = new JTextField();
        nameText.setFont(h2Font);
        gbc.gridwidth = 3;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 0, 0, 20);
        gbc.weightx = 1;
        createRecipePanel.add(nameText, gbc);

        JLabel descriptionLabel = new JLabel("Description");
        descriptionLabel.setFont(h1Font);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 20, 0, 0);
        gbc.weightx = 0.1;
        createRecipePanel.add(descriptionLabel, gbc);

        JTextField descriptionText = new JTextField();
        descriptionText.setFont(h2Font);
        gbc.gridwidth = 3;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 0, 20);
        gbc.weightx = 1;
        createRecipePanel.add(descriptionText, gbc);

        JLabel tagsLabel = new JLabel("Tags");
        tagsLabel.setFont(h1Font);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 20, 0, 0);
        gbc.weightx = 0.1;
        createRecipePanel.add(tagsLabel, gbc);

        JTextField tagsText = new JTextField();
        tagsText.setFont(h2Font);
        gbc.gridwidth = 3;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.insets = new Insets(5, 0, 0, 20);
        createRecipePanel.add(tagsText, gbc);

        JLabel ingredientsLabel = new JLabel("Ingredients");
        ingredientsLabel.setFont(h1Font);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 20, 0, 0);
        gbc.weightx = 0.1;
        createRecipePanel.add(ingredientsLabel, gbc);

        JTextArea ingredientsTextArea = new JTextArea(10, 20);
        ingredientsTextArea.setFont(h2Font);
        JScrollPane paneIngredients = new JScrollPane(ingredientsTextArea);
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 3;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 0, 0, 20);
        gbc.weightx = 1;
        createRecipePanel.add(paneIngredients, gbc);

        JLabel instructionsLabel = new JLabel("Instructions");
        instructionsLabel.setFont(h1Font);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 20, 0, 0);
        gbc.weightx = 0.1;
        createRecipePanel.add(instructionsLabel, gbc);

        JTextArea instructionsTextArea = new JTextArea(10, 20);
        instructionsTextArea.setFont(h2Font);
        JScrollPane paneInstructions = new JScrollPane(instructionsTextArea);
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 3;
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 0, 0, 20);
        gbc.weightx = 1;
        createRecipePanel.add(paneInstructions, gbc);


        Panel donePanel = mkDonePanel();

        Frame createRecipeScreen = new Frame("Create Recipe");
        createRecipeScreen.setLayout(new BorderLayout());
        createRecipeScreen.add(titlePanel,BorderLayout.NORTH);
        createRecipeScreen.add(createRecipePanel,BorderLayout.CENTER);
        createRecipeScreen.add(donePanel,BorderLayout.SOUTH);
        createRecipeScreen.setVisible(true);
        return createRecipeScreen;
    }

    public Frame mkViewRecipeScreen() {

        JLabel label = new JLabel("RECIPE NAME");
        label.setFont(titleFont);

        Panel titlePanel = new Panel(Color.red);
        titlePanel.setLayout(new GridBagLayout());
        titlePanel.add(label);

        Panel actionsPanel = new Panel(Color.blue);
        actionsPanel.setLayout(new GridLayout(3,1));

        Button executeButton = Button.createButton("side",Color.green,"Execute");
        executeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currFrame.setVisible(false);
                currFrame = mkExecuteScreen();
            }
        });

        Button editButton = Button.createButton("side",Color.yellow,"Edit");
        editButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currFrame.setVisible(false);
                currFrame = mkEditScreen();
            }
        });

        Button deleteButton = Button.createButton("side",Color.red,"Delete");
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
        viewRecipeScreen.setVisible(true);
        return viewRecipeScreen;
    }

    public Frame mkExecuteScreen() {

        Button nextButton = Button.createButton("side",Color.green,"Next");

        Panel nextPanel = new Panel(Color.green);
        nextPanel.setLayout(new GridLayout());
        nextPanel.add(nextButton);

        Button prevButton = Button.createButton("side",Color.red,"Prev");

        Panel prevPanel = new Panel(Color.red);
        prevPanel.setLayout(new GridLayout());
        prevPanel.add(prevButton);

        Panel donePanel = mkDonePanel();

        Frame executeScreen = new Frame("Execute Recipe");
        executeScreen.setLayout(new BorderLayout());
        executeScreen.add(nextPanel,BorderLayout.EAST);
        executeScreen.add(prevPanel,BorderLayout.WEST);
        executeScreen.add(donePanel,BorderLayout.SOUTH);
        executeScreen.setVisible(true);
        return executeScreen;
    }

    public Frame mkEditScreen() {

        Frame editScreen = new Frame("Edit Recipe");
        return editScreen;
    }

}
