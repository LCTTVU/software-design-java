package softwaredesign;

import javax.swing.*;

public class Frame extends JFrame {

    Frame(String title) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(title);
        this.setSize(1280,720);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);
    }
}
