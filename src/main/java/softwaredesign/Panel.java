package softwaredesign;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    Panel(Color color, Dimension dimension){
        this.setBackground(color);
        this.setPreferredSize(dimension);
    }
}
