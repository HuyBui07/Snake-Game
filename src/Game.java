import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;

public class Game extends JPanel implements ActionListener, KeyListener {

    private int boardWidth, boardHeight;

    // Walls
    private Walls walls;

    // Snake and food
    private Snake snake;
    private Food food;

    // Key input
    private Queue<Integer> keyCodes = new LinkedList<Integer>();

    // Rendering
    private Renderer renderer;

    // Game state
    private int level;
    private int foodEaten;

    // Game logic
    private int normalDelay = 100;
    private int fastDelay = 40;
    Timer gameLoop;
    boolean isGameOver;

    Game(int boardWidth, int boardHeight) {
        Random random = new Random();

        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setLayout(new BorderLayout());
        addKeyListener(this);
        setFocusable(true);

        // Initialize the game state
        level = 1;
        isGameOver = false;

        // Initialize the walls
        walls = new Walls();
        walls.initializeWalls(level);

        // Initialize the snake
        snake = new Snake(new Tile(1, 0));

        // Initialize the food
        food = new Food(random.nextInt(24), random.nextInt(24));

        // Initialize the renderer
        renderer = new Renderer(walls, snake, food);
        add(renderer, BorderLayout.CENTER);

        // Load sound files
        SoundManager.loadSound("src/sounds/crunch.wav");
        SoundManager.playLowVolumeSound("src/sounds/crunch.wav");

        // Initialize the game loop
        gameLoop = new Timer(normalDelay, this);
        gameLoop.start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (!keyCodes.isEmpty()) {
            snake.changeDirection(keyCodes.poll());
        }

        // Check if the snake has collided with the walls or itself
        if (snake.checkCollision()) {
            isGameOver = true;
        }

        // Check if the snake has collided with the walls
        if (walls.getWall().contains(snake.getHead())) {
            isGameOver = true;
        }

        // Move the snake
        if (!isGameOver) {
            snake.move();
        }

        // Check if the snake has eaten the food
        if (snake.getHead().getX() == food.getX() && snake.getHead().getY() == food.getY()) {
            SoundManager.playCrunchSound();
            snake.grow();

            food.randomizePosition();
            if (snake.contains(food) || walls.contains(food)) {
                food.randomizePosition();
            }

            foodEaten++;

            if (foodEaten == 10) {
                levelUp();
                walls.initializeWalls(level);
            }
        }

        if (isGameOver) {
            renderer.setGameOver(true);
            gameLoop.stop();
        }

        renderer.repaint();

    }

    private void levelUp() {
        level++;
        snake.reset();
        food.randomizePosition();
        foodEaten = 0;
    }

    private void restart() {
        level = 1;
        snake.reset();
        walls.initializeWalls(level);
        food.randomizePosition();
        foodEaten = 0;
        isGameOver = false;
        renderer.setGameOver(false);
        gameLoop.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        // Add the key code to the queue for movement of the snake
        keyCodes.add(keyCode);

        if (keyCode == KeyEvent.VK_SPACE) {
            gameLoop.setDelay(fastDelay);
        }


        if (keyCode == KeyEvent.VK_SPACE && isGameOver) {
            restart();
        }

        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_SPACE) {
            gameLoop.setDelay(100);
        }

    }

}
