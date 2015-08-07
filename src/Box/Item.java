//@author alieb
package Box;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Item {
    public Point point = new Point();
    public String Type = new String();
    public Item() {
    }
    public void setPoint(Point here) {
        point = here;
    }
    public Point getPoint() {
        return point;
    }
    public void setType(String type) {
        Type = type;
    }
    public String getType() {
        return Type;
    }
    public void draw(Graphics g) {
        
    }
    public void step() {
        
    }
}
