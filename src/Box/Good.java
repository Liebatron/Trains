//@author alieb
package Box;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class Good extends Item {
    private final Color brown = new Color(150, 80, 0);
    public boolean loaded;
    public Good (boolean load) {
        loaded = load;
        setType("Good");
    }
    public Point offset (int x, int y) {
        Random random = new Random();
        x = x+random.nextInt(30)-15;
        y = y+random.nextInt(30)-15;
        Point point = new Point(x, y);
        return point;
    }
    public void step() {
        if(loaded) {
            Point move = new Point(getPoint().x+1, getPoint().y);
            setPoint(move);
        }
    }
    @Override
    public void draw(Graphics g) {
        int Y = getPoint().y;
        int X = getPoint().x;
        g.setColor(brown);
        g.fillRect(X, Y, 8, 8);
        g.setColor(Color.BLACK);
        g.drawRect(X, Y, 8, 8);
        g.setColor(brown.darker().darker());
        g.drawLine(X, Y, X+8, Y+8);
        g.drawLine(X, Y+8, X+8, Y);
    }
}
