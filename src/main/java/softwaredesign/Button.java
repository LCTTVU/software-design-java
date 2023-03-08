package softwaredesign;

import javax.swing.*;
import java.awt.*;


class BottomButton extends Button {

    BottomButton(Color color, String text) {
        super(color,text);
        this.setPreferredSize(new Dimension(250,75));
        this.setFont(new Font("Arial",Font.PLAIN,25));
    }
}

class SideButton extends Button {

    SideButton(Color color, String text) {
        super(color,text);
        this.setPreferredSize(new Dimension(100,75));
        this.setFont(new Font("Arial",Font.PLAIN,16));
    }
}

public class Button extends JButton {
    Button(Color color, String text){
        this.setText(text);
        this.setBackground(color);
        this.setFocusable(false);
    }
}
