package softwaredesign;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton{
    Button(Color color, String text, Dimension dimension){
        this.setText(text);
        this.setBackground(color);
        this.setFocusable(false);
        this.setPreferredSize(dimension);
    }


}
