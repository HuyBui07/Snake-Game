package game;

import javax.swing.JPanel;

import config.GameConfig;
import gameObjects.Food;
import gameObjects.Snake;
import gameObjects.Tile;
import gameObjects.Walls;
import models.GameState;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Renderer extends JPanel {
    private Walls walls;
    private Snake snake;
    private Snake snake2;
    private Food food;
    private boolean isGameOver;
    private boolean isDoubleMode;

    public Renderer(Walls walls, Snake snake, Food food) {
        this.walls = walls;
        this.snake = snake;
        this.food = food;

        isDoubleMode = false;
        isGameOver = false;
    }

    public Renderer(Walls walls, Snake snake, Snake snake2, Food food) {
        this.walls = walls;
        this.snake = snake;
        this.snake2 = snake2;
        this.food = food;

        isDoubleMode = true;
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

        // Draw the second snake head
        if (isDoubleMode) {
            switch (snake2.getHeadDirection()) {
                case "UP":
                    g.drawImage(snake2.getImages("HEAD2_UP"), snake2.getHead().getX() * GameConfig.TILE_SIZE,
                            snake2.getHead().getY() * GameConfig.TILE_SIZE,
                            GameConfig.TILE_SIZE, GameConfig.TILE_SIZE, this);
                    break;
                case "DOWN":
                    g.drawImage(snake2.getImages("HEAD2_DOWN"), snake2.getHead().getX() * GameConfig.TILE_SIZE,
                            snake2.getHead().getY() * GameConfig.TILE_SIZE,
                            GameConfig.TILE_SIZE, GameConfig.TILE_SIZE, this);
                    break;
                case "LEFT":
                    g.drawImage(snake2.getImages("HEAD2_LEFT"), snake2.getHead().getX() * GameConfig.TILE_SIZE,
                            snake2.getHead().getY() * GameConfig.TILE_SIZE,
                            GameConfig.TILE_SIZE, GameConfig.TILE_SIZE, this);
                    break;
                case "RIGHT":
                    g.drawImage(snake2.getImages("HEAD2_RIGHT"), snake2.getHead().getX() * GameConfig.TILE_SIZE,
                            snake2.getHead().getY() * GameConfig.TILE_SIZE,
                            GameConfig.TILE_SIZE, GameConfig.TILE_SIZE, this);
                    break;
            }

            // Draw the second snake2 body
            for (int i = 0; i < snake2.getBody().size(); i++) {
                Tile bodyPart = snake2.getBody().get(i);

                if (i == snake2.getBody().size() - 1) {
                    switch (snake2.getTailDirection()) {
                        case "UP":
                            g.drawImage(snake2.getImages("TAIL2_UP"), bodyPart.getX() * GameConfig.TILE_SIZE,
                                    bodyPart.getY() * GameConfig.TILE_SIZE, GameConfig.TILE_SIZE,
                                    GameConfig.TILE_SIZE, this);
                            break;
                        case "DOWN":
                            g.drawImage(snake2.getImages("TAIL2_DOWN"), bodyPart.getX() * GameConfig.TILE_SIZE,
                                    bodyPart.getY() * GameConfig.TILE_SIZE, GameConfig.TILE_SIZE,
                                    GameConfig.TILE_SIZE, this);
                            break;
                        case "LEFT":
                            g.drawImage(snake2.getImages("TAIL2_LEFT"), bodyPart.getX() * GameConfig.TILE_SIZE,
                                    bodyPart.getY() * GameConfig.TILE_SIZE, GameConfig.TILE_SIZE,
                                    GameConfig.TILE_SIZE, this);
                            break;
                        case "RIGHT":
                            g.drawImage(snake2.getImages("TAIL2_RIGHT"), bodyPart.getX() * GameConfig.TILE_SIZE,
                                    bodyPart.getY() * GameConfig.TILE_SIZE, GameConfig.TILE_SIZE,
                                    GameConfig.TILE_SIZE, this);
                            break;
                    }

                    continue;
                }

                Tile previousBodyPart;
                if (i == 0) {
                    previousBodyPart = snake2.getHead();
                } else {
                    previousBodyPart = snake2.getBody().get(i - 1);
                }
                Tile nextBodyPart = snake2.getBody().get(i + 1);

                if (previousBodyPart.getX() == nextBodyPart.getX()) {
                    g.drawImage(snake2.getImages("BODY2_VERTICAL"), bodyPart.getX() * GameConfig.TILE_SIZE,
                            bodyPart.getY() * GameConfig.TILE_SIZE,
                            GameConfig.TILE_SIZE,
                            GameConfig.TILE_SIZE,
                            this);
                } else if (previousBodyPart.getY() == nextBodyPart.getY()) {
                    g.drawImage(snake2.getImages("BODY2_HORIZONTAL"), bodyPart.getX() * GameConfig.TILE_SIZE,
                            bodyPart.getY() * GameConfig.TILE_SIZE, GameConfig.TILE_SIZE,
                            GameConfig.TILE_SIZE,
                            this);
                } else if ((bodyPart.getX() == previousBodyPart.getX() && bodyPart.getY() > previousBodyPart.getY()
                        && bodyPart.getX() > nextBodyPart.getX() && bodyPart.getY() == nextBodyPart.getY())
                        || (bodyPart.getX() > previousBodyPart.getX() && bodyPart.getY() == previousBodyPart.getY()
                                && bodyPart.getX() == nextBodyPart.getX() && bodyPart.getY() > nextBodyPart.getY())) {
                    g.drawImage(snake2.getImages("BODY2_TOP_LEFT"), bodyPart.getX() * GameConfig.TILE_SIZE,
                            bodyPart.getY() * GameConfig.TILE_SIZE,
                            GameConfig.TILE_SIZE,
                            GameConfig.TILE_SIZE, this);
                } else if ((bodyPart.getX() == previousBodyPart.getX() && bodyPart.getY() > previousBodyPart.getY()
                        && bodyPart.getX() < nextBodyPart.getX() && bodyPart.getY() == nextBodyPart.getY())
                        || (bodyPart.getX() < previousBodyPart.getX() && bodyPart.getY() == previousBodyPart.getY()
                                && bodyPart.getX() == nextBodyPart.getX() && bodyPart.getY() > nextBodyPart.getY())) {
                    g.drawImage(snake2.getImages("BODY2_TOP_RIGHT"), bodyPart.getX() * GameConfig.TILE_SIZE,
                            bodyPart.getY() * GameConfig.TILE_SIZE,
                            GameConfig.TILE_SIZE,
                            GameConfig.TILE_SIZE, this);
                } else if ((bodyPart.getX() == previousBodyPart.getX() && bodyPart.getY() < previousBodyPart.getY()
                        && bodyPart.getX() < nextBodyPart.getX() && bodyPart.getY() == nextBodyPart.getY())
                        || (bodyPart.getX() < previousBodyPart.getX() && bodyPart.getY() == previousBodyPart.getY()
                                && bodyPart.getX() == nextBodyPart.getX() && bodyPart.getY() < nextBodyPart.getY())) {
                    g.drawImage(snake2.getImages("BODY2_BOTTOM_RIGHT"), bodyPart.getX() * GameConfig.TILE_SIZE,
                            bodyPart.getY() * GameConfig.TILE_SIZE, GameConfig.TILE_SIZE,
                            GameConfig.TILE_SIZE, this);
                } else if ((bodyPart.getX() == previousBodyPart.getX() && bodyPart.getY() < previousBodyPart.getY()
                        && bodyPart.getX() > nextBodyPart.getX() && bodyPart.getY() == nextBodyPart.getY())
                        || (bodyPart.getX() > previousBodyPart.getX() && bodyPart.getY() == previousBodyPart.getY()
                                && bodyPart.getX() == nextBodyPart.getX() && bodyPart.getY() < nextBodyPart.getY())) {
                    g.drawImage(snake2.getImages("BODY2_BOTTOM_LEFT"), bodyPart.getX() * GameConfig.TILE_SIZE,
                            bodyPart.getY() * GameConfig.TILE_SIZE, GameConfig.TILE_SIZE,
                            GameConfig.TILE_SIZE, this);
                }

            }
        }

        // Draw the timer
        if (isDoubleMode) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Time: " + DoubleGame.countdown, 435, 30);
        }

        // Draw the scoreboard
        if (isGameOver) {
            int boardWidth = isDoubleMode ? GameConfig.BOARD_WIDTH_DOUBLE : GameConfig.BOARD_WIDTH;
            int boardHeight = isDoubleMode ? GameConfig.BOARD_HEIGHT_DOUBLE : GameConfig.BOARD_HEIGHT;

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over", boardWidth / 2 - 100, boardHeight / 2);

            if (isDoubleMode) {
                String winner;
                Color color = Color.white;
                switch (DoubleGame.gameState) {
                    case PLAYER1_WINS:
                        winner = "Player 1 Wins!";
                        color = Color.blue;
                        break;
                    case PLAYER2_WINS:
                        winner = "Player 2 Wins!";
                        color = Color.red;
                        break;
                    default:
                        winner = "Tie";
                        break;
                }
                g.setColor(color);
                g.setFont(new Font("Arial", Font.BOLD, 30));
                g.drawString(winner, boardWidth / 2 - 100, boardHeight / 2 + 50);
            }
        } else if (!isDoubleMode) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score: " + (snake.getBody().size() - 1), 10, 20);
        } else {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Player 1: " + DoubleGame.foodEaten1, 10, 20);
            g.drawString("Player 2: " + DoubleGame.foodEaten2, 890, 20);
        }
    }
}