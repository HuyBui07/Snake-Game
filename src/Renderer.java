import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Renderer extends JPanel {
    private Snake snake;
    private Food food;
    private boolean isGameOver;

    public Renderer(Snake snake, Food food) {
        this.snake = snake;
        this.food = food;

        isGameOver = false;

    }

    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the game board
        for (int i = 0; i < getWidth() / Tile.SIZE; i++) {
            for (int j = 0; j < getHeight() / Tile.SIZE; j++) {
                if ((i + j) % 2 == 0) {
                    g.setColor(new Color(167, 209, 61)); // Dark green
                } else {
                    g.setColor(new Color(175, 215, 70)); // Light green
                }
                g.fillRect(i * Tile.SIZE, j * Tile.SIZE, Tile.SIZE, Tile.SIZE);
            }
        }

        // Draw the food
        g.setColor(Color.RED);
        g.drawImage(food.getImage(), food.getX() * Tile.SIZE, food.getY() * Tile.SIZE, Tile.SIZE, Tile.SIZE, this);

        // Draw the snake head
        g.setColor(Color.BLUE);
        g.fillRect(snake.getHead().getX() * Tile.SIZE, snake.getHead().getY() * Tile.SIZE, Tile.SIZE, Tile.SIZE);

        // Draw the snake body
        for (Tile bodyPart : snake.getBody()) {
            g.setColor(Color.BLUE);
            g.fillRect(bodyPart.getX() * Tile.SIZE, bodyPart.getY() * Tile.SIZE, Tile.SIZE, Tile.SIZE);
        }

        // Draw the scoreboard
        if (isGameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over", 600 / 2 - 100, 600 / 2);
        } else {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score: " + snake.getBody().size(), 10, 20);
        }
    }
}