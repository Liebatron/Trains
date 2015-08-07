//@author alieb
package Box;
import java.util.Random;

public class PointGenerator {
    public int getY(int height) {
        Random random = new Random();
        return random.nextInt(height);
    }
}
