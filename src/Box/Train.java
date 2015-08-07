//@author alieb
package Box;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class Train extends Item {
    private int cars;
    public boolean take;
    private Color color;
    public Train (Rail rail) {
        setType("Train");
        Point start = new Point(0, (rail.getPoint().y)-5);
        setPoint(start);
        Random random = new Random();
        cars = random.nextInt(3)+2;
        if(random.nextInt(20)>11) {
            take = true;
            color = Color.GREEN;
        } else {
            take = false;
            color = Color.cyan;
        }
    }
    public int getCars() {
        return cars;
    }
    public void step() {
        Point move = new Point(getPoint().x+1, getPoint().y);
        setPoint(move);
    }
    @Override
    public void draw(Graphics g) {
        int Y = getPoint().y;
        int X = getPoint().x;
        g.setColor(Color.DARK_GRAY);
        g.fillRoundRect(X, Y, 24, 10, 2, 2);
        g.setColor(Color.BLACK);
        g.drawRoundRect(X, Y, 24, 10, 2, 2);
        g.setColor(color);
        g.fillRect(X+18, Y+4, 3, 3);
        g.drawLine(X+2, Y+8, X+22, Y+8);
        g.drawLine(X+2, Y+2, X+22, Y+2);
    }
}
