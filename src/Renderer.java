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

        setBackground(Color.BLACK);
    }

    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the food
        g.setColor(Color.RED);
        g.fillRect(food.getX() * Tile.SIZE, food.getY() * Tile.SIZE, Tile.SIZE, Tile.SIZE);

        // Draw the snake head
        g.setColor(Color.GREEN);
        g.fillRect(snake.getHead().getX() * Tile.SIZE, snake.getHead().getY() * Tile.SIZE, Tile.SIZE, Tile.SIZE);

        // Draw the snake body
        for (Tile bodyPart : snake.getBody()) {
            g.setColor(Color.GREEN);
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