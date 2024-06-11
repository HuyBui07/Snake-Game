package gameObjects;
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
            case 3:
                // Add walls for level 3
                for (int i = 0; i <= 13; i++) {
                    walls.add(new Tile(i, 4));
                }

                for (int i = 0; i <= 13; i++) {
                    walls.add(new Tile(i, 11));
                }

                for (int i = 0; i <= 13; i++) {
                    walls.add(new Tile(i, 19));
                }

                break;
            default:
                break;
            // Add more cases for more levels...
        }
    }

    public boolean contains(Food tile) {
        for (Tile wall : walls) {
            if (wall.getX() == tile.getX() && wall.getY() == tile.getY()){
                return true;
            }
        }

        return false;
    }
}