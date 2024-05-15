import java.util.ArrayList;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Walls {
    private ArrayList<Tile> walls;
    private Image wallImage;

    public Walls() {
        walls = new ArrayList<Tile>();
        wallImage = new ImageIcon("src/sprites/wood.png").getImage();
    }

    public ArrayList<Tile> getWall() {
        return walls;
    }

    public void addWall(int x, int y) {
        walls.add(new Tile(x, y));
    }

    public void clear() {
        walls.clear();
    }

    public Image getWallImage() {
        return wallImage;
    }

    public void initializeWalls(int level) {
        level = 2;
        walls.clear();
        switch (level) {
            case 2:
                // Add walls for level 2
                for (int i = 0; i <= 14; i++) {
                    walls.add(new Tile(i, 16));
                }

                for (int i = 24; i >= 11; i--) {
                    walls.add(new Tile(i, 7));
                }

                break;
            // Add more cases for more levels...
        }
    }
}