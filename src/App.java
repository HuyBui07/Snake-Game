import javax.swing.*;

import config.GameConfig;
import game.GameMenu;
import models.Player;
import utils.DatabaseConnection;

public class App {
    public static void main(String[] args) throws Exception {

        String playerName = (JOptionPane.showInputDialog("Enter your name: "));
        Player.setName(playerName);

        int highScore = DatabaseConnection.getHighScore(playerName);
        Player.setHighScore(highScore);

        DatabaseConnection.getLeaderBoard();

        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GameConfig.BOARD_WIDTH, GameConfig.BOARD_HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        GameMenu gameMenu = new GameMenu();
        frame.add(gameMenu);
        frame.pack();
        gameMenu.requestFocus();

    }
}
