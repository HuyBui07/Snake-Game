import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {

        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GameConfig.BOARD_WIDTH, GameConfig.BOARD_HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        Game snakeGame = new Game(GameConfig.BOARD_WIDTH, GameConfig.BOARD_HEIGHT);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }
}
