//@author alieb
package Box;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class ItemList {
    private List<Item> items = new ArrayList();
    private PointGenerator gen = new PointGenerator();
    Random random = new Random();
    public final int railSpace = 14;
    public ItemList() {
    }
    public boolean addItem(Item item) {
        boolean valid = true;
        // These two are in here because they're the only items you can add via mouse click
        if(item.getType().equals("Good")) {
            valid = overlapY(item, "Rail", 14);
            if(valid) {
                valid = overlapXY(item, "Good", 2, 2);
            }
        }
        if(item.getType().equals("Rail")) {
            valid = overlapY(item, "Rail", 24);
            if(valid) {
                valid = overlapY(item, "Good", railSpace);
            }
        }
        if(valid) {
        items.add(item);
        }
        return valid;
    }
    public void removeItem(int remove) {
        items.get(remove).setPoint(new Point(620, 10));
    }
    public List<Item> getItems() {
        return items;
    }
    public void progress() {
        for(int i = 0;i<items.size();i++) {
            Item item = items.get(i);
            String type = item.Type;
            item.step();
            if(type.equals("Rail") && overlapXY(item, "Train", "Car", 48, 12)) {
                int rand = random.nextInt(300);
                if(rand == 1) {
                    Train train = new Train((Rail)items.get(i));
                    items.add(train);
                    int y = train.point.y;
                    for(int j=0; j<train.getCars();j++) {
                        Car car = new Car(-26-(j*26) , y, train.take);
                        if(car.back) {
                            Good good = new Good(true);
                            good.setPoint(new Point(car.point.x+3,car.point.y+1));
                            items.add(good);
                            car.backGood=items.lastIndexOf(good);
                        }
                        if(car.front) {
                            Good good = new Good(true);
                            good.setPoint(new Point(car.point.x+13,car.point.y+1));
                            items.add(good);
                            car.frontGood=items.lastIndexOf(good);
                        }
                        items.add(car);
                    }
                }
            } else if(type.equals("Car") && item.point.x>0) {
                if(((Car)item).take==true) {
                    if(random.nextInt(30) == 1) {
                    int found = findNear(item, "Good", 24, 24);
                        if(found != -1) {
                            if(!((Good)items.get(found)).loaded) {
                                if(!((Car)item).back) {
                                    ((Car)item).back = true;
                                    ((Car)item).backGood=found;
                                    items.get(found).setPoint(new Point(item.point.x+3,item.point.y+1));
                                    ((Good)items.get(found)).loaded=true;
                                } else if(!((Car)item).front) {
                                    ((Car)item).front = true;
                                    ((Car)item).frontGood=found;
                                    items.get(found).setPoint(new Point(item.point.x+13,item.point.y+1));
                                    ((Good)items.get(found)).loaded=true;
                                }
                            }
                        }
                    }
                } else {
                    if(random.nextInt(100)==1) {
                        Car car = (Car)item;
                        boolean canUnload = false;
                        for(int tries = 0; tries<500 && !canUnload; tries++) {
                            if(car.back) {
                                Good good = (Good)(items.get(car.backGood));
                                Point unload = good.offset(good.point.x ,good.point.y);
                                if(overlapY(unload.y, "Rail", 14)) {
                                    ((Good)items.get(car.backGood)).point=unload;
                                    ((Good)items.get(car.backGood)).loaded=false;
                                    car.back=false;
                                    car.backGood=0;
                                    canUnload = true;
                                }
                            }
                            if(car.front) {
                                Good good = (Good)(items.get(car.frontGood));
                                Point unload = good.offset(good.point.x ,good.point.y);
                                if(overlapY(unload.y, "Rail", 14)) {
                                    ((Good)items.get(car.frontGood)).point=unload;
                                    ((Good)items.get(car.frontGood)).loaded=false;
                                    car.front=false;
                                    car.frontGood=0;
                                    canUnload = true;
                                }
                            }
                        }
                    }
                }
            } else {
            }
        }
        for(int i = 0;i<items.size();i++) {
            Item item = items.get(i);
            if(item.point.x>632) {
                if(item.Type.equals("Train")) {
                    items.remove(i);
                    redoGoods(i);
                    i--;
                } else if(item.Type.equals("Car")) {
                    boolean removedBack = false;
                    if(((Car)item).back) {
                        int back = ((Car)item).backGood;
                        items.remove(((Car)item).backGood);
                        redoGoods(back);
                        removedBack = true;
                        if(back<i) {
                            i--;
                        }
                    }
                    if(((Car)item).front) {
                        int front = ((Car)item).frontGood;
                        items.remove(front);
                        redoGoods(front);
                        if(front<i) {
                            i--;
                        }
                    }
                    items.remove(i);
                    redoGoods(i);
                    i--;
                }
            }
        }
    }
    public void redoGoods(int removedIndex) {
        for(int i = removedIndex-1;i<items.size();i++) {
            if(items.get(i).Type.equals("Car")) {
                ((Car)items.get(i)).shiftGoods(removedIndex);
            }
        }
    }
    public void draw(Graphics g) {
        List<Item> goods = new ArrayList();
        for(int i = 0;i<items.size();i++) {
            if(items.get(i).Type.equals("Good")) {
                goods.add(items.get(i));
            } else {
            items.get(i).draw(g);
            }
        }
        for(int j = 0;j<goods.size();j++) {
            goods.get(j).draw(g);
        }
    }
    // Everything below this is collision detection. I kept it in here to save on frame-processing
    public int findNear(Item item, String check, int spaceX, int spaceY) {
        int y = item.point.y;
        int x = item.point.x;
        int index = -1;
        Point extant = new Point();
        for(int i = 0;i<items.size();i++) {
            extant = items.get(i).getPoint();
            if(items.get(i).getType().equals(check)) {
                if((x > extant.x-spaceX && x < extant.x+spaceX) && (y > extant.y-spaceY && y < extant.y+spaceY)) {
                    index = i;
                }
            }
        }
        return index;
    }
    public int findNear(int x, int y, String check, int spaceX, int spaceY) {
        int index = -1;
        Point extant = new Point();
        for(int i = 0;i<items.size();i++) {
            extant = items.get(i).getPoint();
            if(items.get(i).getType().equals(check)) {
                if((x > extant.x-spaceX && x < extant.x+spaceX) && (y > extant.y-spaceY && y < extant.y+spaceY)) {
                    index = i;
                }
            }
        }
        return index;
    }
    public boolean overlapY(Item item, String check, int space) {
        int y = item.point.y;
        boolean valid = true;
        Point extant = new Point();
        for(int i = 0;i<items.size();i++) {
            extant = items.get(i).getPoint();
            if(items.get(i).getType().equals(check)) {
                if(y > extant.y-space && y < extant.y+space) {
                    valid = false;
                }
            }
        }
        return valid;
    }
    public boolean overlapY(int y, String check, int space) {
        boolean valid = true;
        Point extant = new Point();
        for(int i = 0;i<items.size();i++) {
            extant = items.get(i).getPoint();
            if(items.get(i).getType().equals(check)) {
                if(y > extant.y-space && y < extant.y+space) {
                    valid = false;
                }
            }
        }
        return valid;
    }
    public boolean overlapXY(int x, int y, String check, int spaceX, int spaceY) {
        boolean valid = true;
        Point extant = new Point();
        for(int i = 0;i<items.size();i++) {
            extant = items.get(i).getPoint();
            if(items.get(i).getType().equals(check)) {
                if((x > extant.x-spaceX && x < extant.x+spaceX) && (y > extant.y-spaceY && y < extant.y+spaceY)) {
                    valid = false;
                }
            }
        }
        return valid;
    }
    public boolean overlapXY(Item item, String check, int spaceX, int spaceY) {
        int x = item.point.x; 
        int y = item.point.y;
        boolean valid = true;
        Point extant = new Point();
        for(int i = 0;i<items.size();i++) {
            extant = items.get(i).getPoint();
            if(items.get(i).getType().equals(check)) {
                if((x > extant.x-spaceX && x < extant.x+spaceX) && (y > extant.y-spaceY && y < extant.y+spaceY)) {
                    valid = false;
                }
            }
        }
        return valid;
    }
    public boolean overlapXY(Item item, String check, String alsoCheck, int spaceX, int spaceY) {
        int x = item.point.x; 
        int y = item.point.y;
        boolean valid = true;
        Point extant = new Point();
        for(int i = 0;i<items.size();i++) {
            extant = items.get(i).getPoint();
            if(items.get(i).Type.equals(check) || items.get(i).Type.equals(alsoCheck)) {
                if((x > extant.x-spaceX && x < extant.x+spaceX) && (y > extant.y-spaceY && y < extant.y+spaceY)) {
                    valid = false;
                }
            }
        }
        return valid;
    }
}