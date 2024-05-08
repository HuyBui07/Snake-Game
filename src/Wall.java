import java.util.ArrayList;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Wall {
    private ArrayList<Tile> wall;
    private Image wallImage;

    public Wall() {
        wall = new ArrayList<Tile>();
        wallImage = new ImageIcon("src/sprites/wood.png").getImage();
    }

    public ArrayList<Tile> getWall() {
        return wall;
    }
    
    public void addWall(int x, int y) {
        wall.add(new Tile(x, y));
    }

    public void clear() {
        wall.clear();
    }

    public Image getWallImage() {
        return wallImage;
    }
}


// public class Game implements ActionListener, KeyListener {
//     // Other variables...
//     private List<Wall> walls;

//     public Game(int boardWidth, int boardHeight) {
//         // Other initialization...
//         walls = new ArrayList<>();
//     }

//     @Override
//     public void actionPerformed(ActionEvent e) {
//         // Game logic...

//         // Check if the snake has collided with any walls
//         for (Wall wall : walls) {
//             if (snake.getHead().getX() == wall.getX() && snake.getHead().getY() == wall.getY()) {
//                 isGameOver = true;
//                 break;
//             }
//         }

//         // Other game logic...
//     }

//     private void levelUp() {
//         level++;
//         // Adjust game parameters based on level
//         // For example, increase the speed of the snake
//         gameLoop.setDelay(100 / level);

//         // Add new walls
//         initializeWalls();
//     }

//     private void initializeWalls() {
//         walls.clear();
//         switch (level) {
//             case 1:
//                 // Add walls for level 1
//                 walls.add(new Wall(5, 5));
//                 walls.add(new Wall(5, 6));
//                 walls.add(new Wall(6, 5));
//                 walls.add(new Wall(6, 6));
//                 break;
//             case 2:
//                 // Add walls for level 2
//                 walls.add(new Wall(5, 5));
//                 walls.add(new Wall(5, 6));
//                 walls.add(new Wall(6, 5));
//                 walls.add(new Wall(6, 6));
//                 walls.add(new Wall(7, 7));
//                 walls.add(new Wall(7, 8));
//                 break;
//             // Add more cases for more levels...
//         }
//     }

//     // Other methods...
// }