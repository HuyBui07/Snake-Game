package gameObjects;
import javax.swing.ImageIcon;

import config.GameConfig;

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

    public void randomizePosition(boolean isDoubleMode) {
        if (isDoubleMode) {
            setX((int) (Math.random() * GameConfig.TILE_NUMBER_X_DOUBLE));
            setY((int) (Math.random() * GameConfig.TILE_NUMBER_Y_DOUBLE));
        } else {
            setX((int) (Math.random() * GameConfig.TILE_NUMBER_X));
            setY((int) (Math.random() * GameConfig.TILE_NUMBER_Y));
        }
    }

}
