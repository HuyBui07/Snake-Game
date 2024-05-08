import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Renderer extends JPanel {
    private Snake snake;
    private Food food;
    private boolean isGameOver;

    // Images
    private Image headImageUp, headImageDown, headImageLeft, headImageRight, bodyHorizontalImage, bodyTopLeftImage,
            bodyTopRightImage, bodyBottomRightImage, bodyBottomLeftImage, bodyVerticalImage,
            tailImageUp, tailImageDown,
            tailImageLeft, tailImageRight, foodImage;

    public Renderer(Snake snake, Food food) {
        this.snake = snake;
        this.food = food;

        isGameOver = false;

        // Load images
        headImageUp = new ImageIcon("src/sprites/head_up.png").getImage();
        headImageDown = new ImageIcon("src/sprites/head_down.png").getImage();
        headImageLeft = new ImageIcon("src/sprites/head_left.png").getImage();
        headImageRight = new ImageIcon("src/sprites/head_right.png").getImage();

        bodyHorizontalImage = new ImageIcon("src/sprites/body_horizontal.png").getImage();
        bodyVerticalImage = new ImageIcon("src/sprites/body_vertical.png").getImage();
        bodyTopLeftImage = new ImageIcon("src/sprites/body_topleft.png").getImage();
        bodyTopRightImage = new ImageIcon("src/sprites/body_topright.png").getImage();
        bodyBottomRightImage = new ImageIcon("src/sprites/body_bottomright.png").getImage();
        bodyBottomLeftImage = new ImageIcon("src/sprites/body_bottomleft.png").getImage();

        tailImageUp = new ImageIcon("src/sprites/tail_up.png").getImage();
        tailImageDown = new ImageIcon("src/sprites/tail_down.png").getImage();
        tailImageLeft = new ImageIcon("src/sprites/tail_left.png").getImage();
        tailImageRight = new ImageIcon("src/sprites/tail_right.png").getImage();
        foodImage = new ImageIcon("src/sprites/apple.png").getImage();
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
        g.drawImage(foodImage, food.getX() * Tile.SIZE, food.getY() * Tile.SIZE, Tile.SIZE, Tile.SIZE, this);

        // Draw the snake head
        switch (snake.getHeadDirection()) {
            case "UP":
                g.drawImage(headImageUp, snake.getHead().getX() * Tile.SIZE, snake.getHead().getY() * Tile.SIZE,
                        Tile.SIZE, Tile.SIZE, this);
                break;
            case "DOWN":
                g.drawImage(headImageDown, snake.getHead().getX() * Tile.SIZE, snake.getHead().getY() * Tile.SIZE,
                        Tile.SIZE, Tile.SIZE, this);
                break;
            case "LEFT":
                g.drawImage(headImageLeft, snake.getHead().getX() * Tile.SIZE, snake.getHead().getY() * Tile.SIZE,
                        Tile.SIZE, Tile.SIZE, this);
                break;
            case "RIGHT":
                g.drawImage(headImageRight, snake.getHead().getX() * Tile.SIZE, snake.getHead().getY() * Tile.SIZE,
                        Tile.SIZE, Tile.SIZE, this);
                break;
        }

        // Draw the snake body
        for (int i = 0; i < snake.getBody().size(); i++) {
            Tile bodyPart = snake.getBody().get(i);

            if (i == snake.getBody().size() - 1) {
                switch (snake.getTailDirection()) {
                    case "UP":
                        g.drawImage(tailImageUp, bodyPart.getX() * Tile.SIZE, bodyPart.getY() * Tile.SIZE, Tile.SIZE,
                                Tile.SIZE, this);
                        break;
                    case "DOWN":
                        g.drawImage(tailImageDown, bodyPart.getX() * Tile.SIZE, bodyPart.getY() * Tile.SIZE, Tile.SIZE,
                                Tile.SIZE, this);
                        break;
                    case "LEFT":
                        g.drawImage(tailImageLeft, bodyPart.getX() * Tile.SIZE, bodyPart.getY() * Tile.SIZE, Tile.SIZE,
                                Tile.SIZE, this);
                        break;
                    case "RIGHT":
                        g.drawImage(tailImageRight, bodyPart.getX() * Tile.SIZE, bodyPart.getY() * Tile.SIZE, Tile.SIZE,
                                Tile.SIZE, this);
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
                g.drawImage(bodyVerticalImage, bodyPart.getX() * Tile.SIZE, bodyPart.getY() * Tile.SIZE, Tile.SIZE,
                        Tile.SIZE,
                        this);
            } else if (previousBodyPart.getY() == nextBodyPart.getY()) {
                g.drawImage(bodyHorizontalImage, bodyPart.getX() * Tile.SIZE, bodyPart.getY() * Tile.SIZE, Tile.SIZE,
                        Tile.SIZE,
                        this);
            } else if ((bodyPart.getX() == previousBodyPart.getX() && bodyPart.getY() > previousBodyPart.getY()
                    && bodyPart.getX() > nextBodyPart.getX() && bodyPart.getY() == nextBodyPart.getY())
                    || (bodyPart.getX() > previousBodyPart.getX() && bodyPart.getY() == previousBodyPart.getY()
                            && bodyPart.getX() == nextBodyPart.getX() && bodyPart.getY() > nextBodyPart.getY())) {
                g.drawImage(bodyTopLeftImage, bodyPart.getX() * Tile.SIZE, bodyPart.getY() * Tile.SIZE, Tile.SIZE,
                        Tile.SIZE, this);
            } else if ((bodyPart.getX() == previousBodyPart.getX() && bodyPart.getY() > previousBodyPart.getY()
                    && bodyPart.getX() < nextBodyPart.getX() && bodyPart.getY() == nextBodyPart.getY())
                    || (bodyPart.getX() < previousBodyPart.getX() && bodyPart.getY() == previousBodyPart.getY()
                            && bodyPart.getX() == nextBodyPart.getX() && bodyPart.getY() > nextBodyPart.getY())) {
                g.drawImage(bodyTopRightImage, bodyPart.getX() * Tile.SIZE, bodyPart.getY() * Tile.SIZE, Tile.SIZE,
                        Tile.SIZE, this);
            } else if ((bodyPart.getX() == previousBodyPart.getX() && bodyPart.getY() < previousBodyPart.getY()
                    && bodyPart.getX() < nextBodyPart.getX() && bodyPart.getY() == nextBodyPart.getY())
                    || (bodyPart.getX() < previousBodyPart.getX() && bodyPart.getY() == previousBodyPart.getY()
                            && bodyPart.getX() == nextBodyPart.getX() && bodyPart.getY() < nextBodyPart.getY())) {
                g.drawImage(bodyBottomRightImage, bodyPart.getX() * Tile.SIZE, bodyPart.getY() * Tile.SIZE, Tile.SIZE,
                        Tile.SIZE, this);
            } else if ((bodyPart.getX() == previousBodyPart.getX() && bodyPart.getY() < previousBodyPart.getY()
                    && bodyPart.getX() > nextBodyPart.getX() && bodyPart.getY() == nextBodyPart.getY())
                    || (bodyPart.getX() > previousBodyPart.getX() && bodyPart.getY() == previousBodyPart.getY()
                            && bodyPart.getX() == nextBodyPart.getX() && bodyPart.getY() < nextBodyPart.getY())) {
                g.drawImage(bodyBottomLeftImage, bodyPart.getX() * Tile.SIZE, bodyPart.getY() * Tile.SIZE, Tile.SIZE,
                        Tile.SIZE, this);
            } else {
                g.setColor(Color.BLACK);
                g.fillRect(bodyPart.getX() * Tile.SIZE, bodyPart.getY() * Tile.SIZE, Tile.SIZE, Tile.SIZE);
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