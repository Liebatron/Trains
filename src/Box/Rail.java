//@author alieb
package Box;

import java.awt.Color;
import java.awt.Graphics;

public class Rail extends Item {
    private final Color brown = new Color(150, 80, 0);
    public Rail() {
        setType("Rail");
    }
    public void step() {
    }
    @Override
    public void draw(Graphics g) {
        int Y = getPoint().y;
        int X = 0;
        g.setColor(brown);
        while(X<600) {
            g.drawLine(X, Y+3, X, Y-3);
            X+=6;
        }
        g.setColor(Color.BLACK);
        g.drawLine(0,Y-2,600,Y-2);
        g.drawLine(0,Y+2,600,Y+2);
    }
}
    
    
