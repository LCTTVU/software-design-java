package softwaredesign;

import javax.swing.*;

import java.awt.*;


class CRLabel extends Label {
    CRLabel(String text) {
        super(text);
        this.setFont(new Font("Arial",Font.BOLD,18));
    }
}

public class Label extends JLabel {
    Label(String text) {
        this.setText(text);
    }
}
