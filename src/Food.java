import javax.swing.ImageIcon;
import java.awt.Image;

public class Food extends Tile {

    public Food(int x, int y) {
        super(x, y);
    }

    public void randomizePosition() {
        setX((int) (Math.random() * 24));
        setY((int) (Math.random() * 24));
    }

}
