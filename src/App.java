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

        boolean authenticated = false;
        while (!authenticated) {
            Object[] options = { "Log in", "Sign up" };
            int option = JOptionPane.showOptionDialog(null, message, "Snake Game", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE, customIcon, options, options[0]);
            System.out.println(option);
            if (option == 0) {
                // Log in button was pressed
                String playerName = playerNameField.getText();
                String password = new String(passwordField.getPassword());

                if (!DatabaseConnection.authenticateUser(playerName, password)) {
                    JOptionPane.showMessageDialog(null, "Invalid player's name or password. Please try again.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    int highScore = DatabaseConnection.getHighScore(Player.getId());
                    Player.setHighScore(highScore);
                    authenticated = true;
                }
            } else if (option == 1) {
                // Sign up button was pressed
                String playerName = playerNameField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(passwordField.getPassword());

                if (playerName.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Player's name and password cannot be empty. Please try again.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match. Please try again.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else if (DatabaseConnection.checkPlayerName(playerName)) {
                    JOptionPane.showMessageDialog(null, "Player's name already exists. Please try again.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    DatabaseConnection.addPlayer(playerName, password);
                    int playerId = DatabaseConnection.getPlayerId(playerName);
                    Player.setId(playerId);
                    Player.setName(playerName);
                    Player.setHighScore(0);
                    authenticated = true;
                }
            } else {
                // User closed the dialog or clicked on something else
                System.exit(0);
            }
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
