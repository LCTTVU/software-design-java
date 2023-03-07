package softwaredesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Button extends JButton implements ActionListener {
    Button(Color color, String text, Dimension dimension){
        this.setText(text);
        this.setBackground(color);
        this.addActionListener(this);
        this.setFocusable(false);
        this.setPreferredSize(dimension);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this) {
            System.out.println("create recipe");
        }
    }
}
