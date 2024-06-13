package game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import config.GameConfig;
import gameObjects.Food;
import gameObjects.Snake;
import gameObjects.Tile;
import gameObjects.Walls;
import models.GameState;
import utils.SoundManager;

import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;

public class DoubleGame extends JPanel implements ActionListener, KeyListener {

    private int boardWidth = GameConfig.BOARD_WIDTH_DOUBLE;
    private int boardHeight = GameConfig.BOARD_HEIGHT_DOUBLE;

    // Walls
    private Walls walls;

    // Snake and food
    private Snake snake;
    private Snake snake2;
    private Food food;

    // Key input
    private Queue<Integer> keyCodes = new LinkedList<Integer>();
    private Queue<Integer> keyCodes2 = new LinkedList<Integer>();

    // Rendering
    private Renderer renderer;

    // Game state
    private int level;
    public static GameState gameState = GameState.RUNNING;
    public static int foodEaten1 = 0;
    public static int foodEaten2 = 0;

    // Game logic
    private int normalDelay = 100;
    Timer gameLoop;
    boolean isGameOver;
    public static int countdown = 60;
    Timer countdownTimer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            countdown--;
            if (countdown == 0) {
                countdownTimer.stop();
                if (foodEaten1 > foodEaten2) {
                    gameState = GameState.PLAYER1_WINS;
                } else if (foodEaten1 < foodEaten2) {
                    gameState = GameState.PLAYER2_WINS;
                } else {
                    gameState = GameState.TIE;
                }
                isGameOver = true;
            }
        }
    });

    // Game mode
    private boolean isDoubleMode = true;

    DoubleGame() {
        Random random = new Random();

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
        snake2 = new Snake(new Tile(23, 31));

        // Initialize the food
        int foodX = isDoubleMode ? random.nextInt(40) : random.nextInt(24);
        int foodY = isDoubleMode ? random.nextInt(32) : random.nextInt(24);
        food = new Food(foodX, foodY);

        // Initialize the renderer
        renderer = new Renderer(walls, snake, snake2, food);
        add(renderer, BorderLayout.CENTER);

        // Load sound files
        SoundManager.loadSound("src/sounds/crunch.wav");
        SoundManager.playLowVolumeSound("src/sounds/crunch.wav");

        // Initialize the game loop
        gameLoop = new Timer(normalDelay, this);
        gameLoop.start();
        countdownTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!keyCodes.isEmpty()) {
            snake.changeDirection(keyCodes.poll());
        }

        if (!keyCodes2.isEmpty()) {
            snake2.changeDirection(keyCodes2.poll());
        }

        // Check if snakes have collided with the walls or itself
        if (snake.checkCollision(isDoubleMode)) {
            gameState = GameState.PLAYER2_WINS;
            isGameOver = true;
        } else if (snake2.checkCollision(isDoubleMode)) {
            gameState = GameState.PLAYER1_WINS;
            isGameOver = true;
        }

        // Check if the snake2 have collided with the walls
        if (walls.getWall().contains(snake.getHead())) {
            gameState = GameState.PLAYER2_WINS;
            isGameOver = true;
        } else if (walls.getWall().contains(snake2.getHead())) {
            gameState = GameState.PLAYER1_WINS;
            isGameOver = true;
        }

        // Check if the snakes collide with each other
        if (snake.getHead().getX() == snake2.getHead().getX() && snake.getHead().getY() == snake2.getHead().getY()) {
            gameState = GameState.TIE;
            isGameOver = true;
        }

        snake.collideWithSnake(snake2);
        snake2.collideWithSnake(snake);

        // Move the snake
        if (!isGameOver) {
            snake.move();
            snake2.move();
        }

        // Check if the snake has eaten the food
        if (snake.getHead().getX() == food.getX() && snake.getHead().getY() == food.getY()) {
            SoundManager.playCrunchSound();
            snake.grow();

            food.randomizePosition(isDoubleMode);
            if (snake.contains(food) || walls.contains(food)) {
                food.randomizePosition(isDoubleMode);
            }

            foodEaten1++;
        }

        if (snake2.getHead().getX() == food.getX() && snake2.getHead().getY() == food.getY()) {
            SoundManager.playCrunchSound();
            snake2.grow();

            food.randomizePosition(isDoubleMode);
            if (snake2.contains(food) || walls.contains(food)) {
                food.randomizePosition(isDoubleMode);
            }

            foodEaten2++;
        }

        if (isGameOver) {
            renderer.setGameOver(true);
            gameLoop.stop();
        }

        renderer.repaint();

    }

    private void restart() {
        level = 1;
        snake.reset();
        snake2.reset2();
        // snake2.resetTest();
        walls.initializeWalls(level);
        food.randomizePosition(isDoubleMode);
        foodEaten1 = 0;
        foodEaten2 = 0;
        isGameOver = false;
        renderer.setGameOver(false);
        countdown = 60;
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

        // Add the key code to the queue for movement of snakes
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_A
                || keyCode == KeyEvent.VK_D) {
            keyCodes.add(keyCode);
        }

        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_LEFT
                || keyCode == KeyEvent.VK_RIGHT) {
            keyCodes2.add(keyCode);
        }

        if (keyCode == KeyEvent.VK_SPACE && isGameOver) {
            restart();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

}
