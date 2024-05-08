import java.util.ArrayList;

public class Snake {
    private Tile head;
    private ArrayList<Tile> body;
    private int velocityX, velocityY;

    public Snake(Tile head) {
        this.head = head;
        this.body = new ArrayList<Tile>();
        this.body.add(new Tile(head.getX(), head.getY() - 1)); // Add a body part below the head
        this.velocityX = 0;
        this.velocityY = 1;
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

}
