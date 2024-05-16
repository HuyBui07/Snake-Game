import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import java.awt.Image;

public class Snake {
    private Tile head;
    private ArrayList<Tile> body;
    private int velocityX, velocityY;
    private Map<String, Image> images;

    public Snake(Tile head) {
        this.head = head;
        this.body = new ArrayList<Tile>();
        this.body.add(new Tile(head.getX() - 1, head.getY())); // Add a body part below the head
        this.velocityX = 1;
        this.velocityY = 0;

        // Load the images for the snake
        this.images = new HashMap<String, Image>();

        this.images.put("HEAD_UP", new ImageIcon("src/sprites/head_up.png").getImage());
        this.images.put("HEAD_DOWN", new ImageIcon("src/sprites/head_down.png").getImage());
        this.images.put("HEAD_LEFT", new ImageIcon("src/sprites/head_left.png").getImage());
        this.images.put("HEAD_RIGHT", new ImageIcon("src/sprites/head_right.png").getImage());

        this.images.put("BODY_VERTICAL", new ImageIcon("src/sprites/body_vertical.png").getImage());
        this.images.put("BODY_HORIZONTAL", new ImageIcon("src/sprites/body_horizontal.png").getImage());

        this.images.put("BODY_TOP_LEFT", new ImageIcon("src/sprites/body_topleft.png").getImage());
        this.images.put("BODY_TOP_RIGHT", new ImageIcon("src/sprites/body_topright.png").getImage());
        this.images.put("BODY_BOTTOM_LEFT", new ImageIcon("src/sprites/body_bottomleft.png").getImage());
        this.images.put("BODY_BOTTOM_RIGHT", new ImageIcon("src/sprites/body_bottomright.png").getImage());

        this.images.put("TAIL_UP", new ImageIcon("src/sprites/tail_up.png").getImage());
        this.images.put("TAIL_DOWN", new ImageIcon("src/sprites/tail_down.png").getImage());
        this.images.put("TAIL_LEFT", new ImageIcon("src/sprites/tail_left.png").getImage());
        this.images.put("TAIL_RIGHT", new ImageIcon("src/sprites/tail_right.png").getImage());

    }

    public Tile getHead() {
        return head;
    }

    public ArrayList<Tile> getBody() {
        return body;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setHead(Tile head) {
        this.head = head;
    }

    public void setBody(ArrayList<Tile> body) {
        this.body = body;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public Image getImages(String key) {
        return images.get(key);
    }

    public void changeDirection(int keyCode) {
        switch (keyCode) {
            case 37:
                if (velocityX != 1) {
                    velocityX = -1;
                    velocityY = 0;
                }
                break;
            case 38:
                if (velocityY != 1) {
                    velocityX = 0;
                    velocityY = -1;
                }
                break;
            case 39:
                if (velocityX != -1) {
                    velocityX = 1;
                    velocityY = 0;
                }
                break;
            case 40:
                if (velocityY != -1) {
                    velocityX = 0;
                    velocityY = 1;
                }
                break;
        }
    }

    public String getHeadDirection() {
        if (velocityX == 1) {
            return "RIGHT";
        } else if (velocityX == -1) {
            return "LEFT";
        } else if (velocityY == 1) {
            return "DOWN";
        } else if (velocityY == -1) {
            return "UP";
        }
        return null;
    }

    public String getTailDirection() {

        Tile tail = body.get(body.size() - 1);
        Tile beforeTail;

        if (body.size() == 1) {
            beforeTail = head;
        } else {
            beforeTail = body.get(body.size() - 2);
        }

        if (tail.getX() == beforeTail.getX() && tail.getY() < beforeTail.getY()) {
            return "UP";
        } else if (tail.getX() == beforeTail.getX() && tail.getY() > beforeTail.getY()) {
            return "DOWN";
        } else if (tail.getX() < beforeTail.getX() && tail.getY() == beforeTail.getY()) {
            return "LEFT";
        } else if (tail.getX() > beforeTail.getX() && tail.getY() == beforeTail.getY()) {
            return "RIGHT";
        }
        return null;

    }

    public void move() {
        this.head = new Tile(head.getX() + velocityX, head.getY() + velocityY);
        for (int i = body.size() - 1; i >= 0; i--) {
            if (i == 0) {
                body.set(i, new Tile(head.getX() - velocityX, head.getY() - velocityY));
                continue;
            }
            body.set(i, body.get(i - 1));
        }
    }

    public void grow() {
        Tile tail = body.get(body.size() - 1);
        Tile beforeTail;

        if (body.size() == 1) {
            beforeTail = head;
        } else {
            beforeTail = body.get(body.size() - 2);
        }

        if (tail.getX() == beforeTail.getX() && tail.getY() < beforeTail.getY()) {
            body.add(new Tile(tail.getX(), tail.getY() - 1));
        } else if (tail.getX() == beforeTail.getX() && tail.getY() > beforeTail.getY()) {
            body.add(new Tile(tail.getX(), tail.getY() + 1));
        } else if (tail.getX() < beforeTail.getX() && tail.getY() == beforeTail.getY()) {
            body.add(new Tile(tail.getX() - 1, tail.getY()));
        } else if (tail.getX() > beforeTail.getX() && tail.getY() == beforeTail.getY()) {
            body.add(new Tile(tail.getX() + 1, tail.getY()));
        }
    }

    public boolean checkCollision() {
        // Check if the snake has collided with the walls
        if (head.getX() < 0 || head.getX() >= 24 || head.getY() < 0 || head.getY() >= 24) {
            return true;
        }

        // Check if the snake has collided with itself
        for (Tile tile : body) {
            if (head.getX() == tile.getX() && head.getY() == tile.getY()) {
                return true;
            }
        }

        return false;
    }

    public void reset() {
        head = new Tile(12, 12);
        body.clear();
        body.add(new Tile(12, 11));
        velocityX = 0;
        velocityY = 1;
    }

    public boolean contains(Tile tile) {
        if (head.equals(tile)) {
            return true;
        }

        for (Tile bodyTile : body) {
            if (bodyTile.getX() == tile.getX() && bodyTile.getY() == tile.getY()) {
                return true;
            }
        }

        return false;
    }
}
