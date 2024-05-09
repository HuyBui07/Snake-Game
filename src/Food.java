import javax.swing.ImageIcon;
import java.awt.Image;

public class Food extends Tile {
    private Image foodImage;

    public Food(int x, int y) {
        super(x, y);
        foodImage = new ImageIcon("src/sprites/apple.png").getImage();
    }

    public Image getFoodImage() {
        return foodImage;
    }

    public void randomizePosition() {
        setX((int) (Math.random() * 24));
        setY((int) (Math.random() * 24));
    }

}
