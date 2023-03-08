package softwaredesign;

import javax.swing.*;
import java.awt.*;


public class Panel extends JPanel {

    Panel(Color color){
        this.setBackground(color);
        this.setPreferredSize(new Dimension(100,90));
    }
}
