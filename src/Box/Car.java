//@author alieb
package Box;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class Car extends Item {
    private Color color;
    public boolean front;
    public boolean back;
    public boolean take;
    public int frontGood;
    public int backGood;
    public Car (int startX, int startY, boolean status) {
        setType("Car");
        setPoint(new Point(startX, startY));
        take = status;
        if(!take) {
            Random random = new Random();
            front = random.nextBoolean();
            back = random.nextBoolean();
        }
        Random random = new Random();
        color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }
    public void shiftGoods(int whereRemoved) {
        if(frontGood>whereRemoved) {
            frontGood --;        
        }
        if(backGood>whereRemoved) {
            backGood --;
        }
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
        g.fillRect(X, Y, 24, 10);
        g.setColor(Color.BLACK);
        g.drawRect(X, Y, 24, 10);
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(X+2, Y+8, X+22, Y+8);
        g.drawLine(X+2, Y+2, X+22, Y+2);
        g.setColor(color);
        g.drawLine(X+2, Y+5, X+22, Y+5);
    }
}

