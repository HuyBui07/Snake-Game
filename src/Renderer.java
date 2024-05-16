import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Renderer extends JPanel {
    private Walls walls;
    private Snake snake;
    private Food food;
    private boolean isGameOver;

    public Renderer(Walls walls, Snake snake, Food food) {
        this.walls = walls;
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
        for (int i = 0; i < getWidth() / GameConfig.TILE_SIZE; i++) {
            for (int j = 0; j < getHeight() / GameConfig.TILE_SIZE; j++) {
                if ((i + j) % 2 == 0) {
                    g.setColor(new Color(167, 209, 61)); // Dark green
                } else {
                    g.setColor(new Color(175, 215, 70)); // Light green
                }
                g.fillRect(i * GameConfig.TILE_SIZE, j * GameConfig.TILE_SIZE, GameConfig.TILE_SIZE,
                        GameConfig.TILE_SIZE);
            }
        }

        // Draw the walls
        for (Tile wall : walls.getWall()) {
            g.drawImage(walls.getWallImage(), wall.getX() * GameConfig.TILE_SIZE, wall.getY() * GameConfig.TILE_SIZE,
                    GameConfig.TILE_SIZE, GameConfig.TILE_SIZE,
                    this);
        }

        // Draw the food
        g.drawImage(food.getFoodImage(), food.getX() * GameConfig.TILE_SIZE, food.getY() * GameConfig.TILE_SIZE,
                GameConfig.TILE_SIZE, GameConfig.TILE_SIZE, this);

        // Draw the snake head
        switch (snake.getHeadDirection()) {
            case "UP":
                g.drawImage(snake.getImages("HEAD_UP"), snake.getHead().getX() * GameConfig.TILE_SIZE,
                        snake.getHead().getY() * GameConfig.TILE_SIZE,
                        GameConfig.TILE_SIZE, GameConfig.TILE_SIZE, this);
                break;
            case "DOWN":
                g.drawImage(snake.getImages("HEAD_DOWN"), snake.getHead().getX() * GameConfig.TILE_SIZE,
                        snake.getHead().getY() * GameConfig.TILE_SIZE,
                        GameConfig.TILE_SIZE, GameConfig.TILE_SIZE, this);
                break;
            case "LEFT":
                g.drawImage(snake.getImages("HEAD_LEFT"), snake.getHead().getX() * GameConfig.TILE_SIZE,
                        snake.getHead().getY() * GameConfig.TILE_SIZE,
                        GameConfig.TILE_SIZE, GameConfig.TILE_SIZE, this);
                break;
            case "RIGHT":
                g.drawImage(snake.getImages("HEAD_RIGHT"), snake.getHead().getX() * GameConfig.TILE_SIZE,
                        snake.getHead().getY() * GameConfig.TILE_SIZE,
                        GameConfig.TILE_SIZE, GameConfig.TILE_SIZE, this);
                break;
        }

        // Draw the snake body
        for (int i = 0; i < snake.getBody().size(); i++) {
            Tile bodyPart = snake.getBody().get(i);

            if (i == snake.getBody().size() - 1) {
                switch (snake.getTailDirection()) {
                    case "UP":
                        g.drawImage(snake.getImages("TAIL_UP"), bodyPart.getX() * GameConfig.TILE_SIZE,
                                bodyPart.getY() * GameConfig.TILE_SIZE, GameConfig.TILE_SIZE,
                                GameConfig.TILE_SIZE, this);
                        break;
                    case "DOWN":
                        g.drawImage(snake.getImages("TAIL_DOWN"), bodyPart.getX() * GameConfig.TILE_SIZE,
                                bodyPart.getY() * GameConfig.TILE_SIZE, GameConfig.TILE_SIZE,
                                GameConfig.TILE_SIZE, this);
                        break;
                    case "LEFT":
                        g.drawImage(snake.getImages("TAIL_LEFT"), bodyPart.getX() * GameConfig.TILE_SIZE,
                                bodyPart.getY() * GameConfig.TILE_SIZE, GameConfig.TILE_SIZE,
                                GameConfig.TILE_SIZE, this);
                        break;
                    case "RIGHT":
                        g.drawImage(snake.getImages("TAIL_RIGHT"), bodyPart.getX() * GameConfig.TILE_SIZE,
                                bodyPart.getY() * GameConfig.TILE_SIZE, GameConfig.TILE_SIZE,
                                GameConfig.TILE_SIZE, this);
                        break;
                }

                continue;
            }

            Tile previousBodyPart;
            if (i == 0) {
                previousBodyPart = snake.getHead();
            } else {
                previousBodyPart = snake.getBody().get(i - 1);
            }
            Tile nextBodyPart = snake.getBody().get(i + 1);

            if (previousBodyPart.getX() == nextBodyPart.getX()) {
                g.drawImage(snake.getImages("BODY_VERTICAL"), bodyPart.getX() * GameConfig.TILE_SIZE,
                        bodyPart.getY() * GameConfig.TILE_SIZE,
                        GameConfig.TILE_SIZE,
                        GameConfig.TILE_SIZE,
                        this);
            } else if (previousBodyPart.getY() == nextBodyPart.getY()) {
                g.drawImage(snake.getImages("BODY_HORIZONTAL"), bodyPart.getX() * GameConfig.TILE_SIZE,
                        bodyPart.getY() * GameConfig.TILE_SIZE, GameConfig.TILE_SIZE,
                        GameConfig.TILE_SIZE,
                        this);
            } else if ((bodyPart.getX() == previousBodyPart.getX() && bodyPart.getY() > previousBodyPart.getY()
                    && bodyPart.getX() > nextBodyPart.getX() && bodyPart.getY() == nextBodyPart.getY())
                    || (bodyPart.getX() > previousBodyPart.getX() && bodyPart.getY() == previousBodyPart.getY()
                            && bodyPart.getX() == nextBodyPart.getX() && bodyPart.getY() > nextBodyPart.getY())) {
                g.drawImage(snake.getImages("BODY_TOP_LEFT"), bodyPart.getX() * GameConfig.TILE_SIZE,
                        bodyPart.getY() * GameConfig.TILE_SIZE,
                        GameConfig.TILE_SIZE,
                        GameConfig.TILE_SIZE, this);
            } else if ((bodyPart.getX() == previousBodyPart.getX() && bodyPart.getY() > previousBodyPart.getY()
                    && bodyPart.getX() < nextBodyPart.getX() && bodyPart.getY() == nextBodyPart.getY())
                    || (bodyPart.getX() < previousBodyPart.getX() && bodyPart.getY() == previousBodyPart.getY()
                            && bodyPart.getX() == nextBodyPart.getX() && bodyPart.getY() > nextBodyPart.getY())) {
                g.drawImage(snake.getImages("BODY_TOP_RIGHT"), bodyPart.getX() * GameConfig.TILE_SIZE,
                        bodyPart.getY() * GameConfig.TILE_SIZE,
                        GameConfig.TILE_SIZE,
                        GameConfig.TILE_SIZE, this);
            } else if ((bodyPart.getX() == previousBodyPart.getX() && bodyPart.getY() < previousBodyPart.getY()
                    && bodyPart.getX() < nextBodyPart.getX() && bodyPart.getY() == nextBodyPart.getY())
                    || (bodyPart.getX() < previousBodyPart.getX() && bodyPart.getY() == previousBodyPart.getY()
                            && bodyPart.getX() == nextBodyPart.getX() && bodyPart.getY() < nextBodyPart.getY())) {
                g.drawImage(snake.getImages("BODY_BOTTOM_RIGHT"), bodyPart.getX() * GameConfig.TILE_SIZE,
                        bodyPart.getY() * GameConfig.TILE_SIZE, GameConfig.TILE_SIZE,
                        GameConfig.TILE_SIZE, this);
            } else if ((bodyPart.getX() == previousBodyPart.getX() && bodyPart.getY() < previousBodyPart.getY()
                    && bodyPart.getX() > nextBodyPart.getX() && bodyPart.getY() == nextBodyPart.getY())
                    || (bodyPart.getX() > previousBodyPart.getX() && bodyPart.getY() == previousBodyPart.getY()
                            && bodyPart.getX() == nextBodyPart.getX() && bodyPart.getY() < nextBodyPart.getY())) {
                g.drawImage(snake.getImages("BODY_BOTTOM_LEFT"), bodyPart.getX() * GameConfig.TILE_SIZE,
                        bodyPart.getY() * GameConfig.TILE_SIZE, GameConfig.TILE_SIZE,
                        GameConfig.TILE_SIZE, this);
            }

        }

        // Draw the scoreboard
        if (isGameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over", 600 / 2 - 100, 600 / 2);
        } else {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score: " + (snake.getBody().size() - 1), 10, 20);
        }
    }
}