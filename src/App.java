import javax.swing.*;
import java.awt.*;

import config.GameConfig;
import game.GameMenu;
import models.Player;
import utils.DatabaseConnection;

public class App {
    private static ImageIcon customIcon = new ImageIcon("src/sprites/apple.png");

    public static void main(String[] args) throws Exception {

        // Set new properties
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 14));
        UIManager.put("OptionPane.messageForeground", Color.GREEN.darker());

        JTextField playerNameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
                "Player's name:", playerNameField,
                "Password:", passwordField
        };

        Object[] options = { "Log in" };
        int option = JOptionPane.showOptionDialog(null, message, "Snake Game", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, customIcon, options, options[0]);
        if (option == 0) {
            // Log in button was pressed
            String playerName = playerNameField.getText();
            String password = new String(passwordField.getPassword());

            int highScore = DatabaseConnection.getHighScore(playerName);
            Player.setHighScore(highScore);
        }

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
