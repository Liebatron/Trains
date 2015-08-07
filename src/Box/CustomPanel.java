//@author alieb
package Box;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Timer;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.util.Random;

class CustomPanel extends JPanel {
    private ItemList list = new ItemList();
    private Timer timer = null;
    Random random = new Random();
    public CustomPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
                    remove(e.getX(),e.getY());
                } else if((e.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
                    addRail(e.getY());
                } else {
                    addGood(e.getX(),e.getY());
                }
            }
        });
        addRail(random.nextInt(450));
        timer = new Timer(60, new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                list.progress();
                repaint();
            }
        });
        timer.start();
    }
    private void addRail(int y) {
        Rail rail = new Rail();
        Point point = new Point(0, y);
        rail.setPoint(point);
        list.addItem(rail);
    }
    private void remove(int getX, int getY) {
        int nearClick = list.findNear(getX, getY, "Good", 9, 9);
        if(nearClick != -1) {
            list.removeItem(nearClick);
        }
    }
    private void addGood(int x, int y) {
        Good good = new Good(false);
        good.setPoint(good.offset(x, y));
        int tries = 0;
        boolean created = false;
        while(tries<200 && !created) {
            created = list.addItem(good);
            tries++;
        }
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600,450);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        list.draw(g);
    }

    private void addActionListener(ActionListener actionListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}