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

// public class Game implements ActionListener, KeyListener {
// // Other variables...
// private List<Wall> walls;

// public Game(int boardWidth, int boardHeight) {
// // Other initialization...
// walls = new ArrayList<>();
// }

// @Override
// public void actionPerformed(ActionEvent e) {
// // Game logic...

// // Check if the snake has collided with any walls
// for (Wall wall : walls) {
// if (snake.getHead().getX() == wall.getX() && snake.getHead().getY() ==
// wall.getY()) {
// isGameOver = true;
// break;
// }
// }

// // Other game logic...
// }

// private void levelUp() {
// level++;
// // Adjust game parameters based on level
// // For example, increase the speed of the snake
// gameLoop.setDelay(100 / level);

// // Add new walls
// initializeWalls();
// }

// private void initializeWalls() {
// walls.clear();
// switch (level) {
// case 1:
// // Add walls for level 1
// walls.add(new Wall(5, 5));
// walls.add(new Wall(5, 6));
// walls.add(new Wall(6, 5));
// walls.add(new Wall(6, 6));
// break;
// case 2:
// // Add walls for level 2
// walls.add(new Wall(5, 5));
// walls.add(new Wall(5, 6));
// walls.add(new Wall(6, 5));
// walls.add(new Wall(6, 6));
// walls.add(new Wall(7, 7));
// walls.add(new Wall(7, 8));
// break;
// // Add more cases for more levels...
// }
// }

// // Other methods...
// }