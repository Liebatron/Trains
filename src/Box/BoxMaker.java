//@author alieb
package Box;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

public class BoxMaker {
    public BoxMaker(){
        prepareGUI();
    }
    private void prepareGUI(){
        JFrame f = new JFrame("Swing Paint Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CustomPanel panel = new CustomPanel();
        panel.setBackground(Color.white);
        f.add(panel);
        f.pack();
        f.setVisible(true);
    }
}
