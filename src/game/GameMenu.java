package game;

import javax.swing.*;

import config.GameConfig;
import models.Player;

import java.awt.*;
import java.awt.event.*;

public class GameMenu extends JPanel {
    private JLabel highScoreLabel;
    private JLabel titleLabel;

    private JButton singlePlayerButton;
    private JButton doublePlayerButton;
    private JButton leaderboardButton;

    public GameMenu() {
        setPreferredSize(new Dimension(GameConfig.BOARD_WIDTH, GameConfig.BOARD_HEIGHT));
        setLayout(new GridBagLayout()); // Set layout manager

       // Create high score label
        highScoreLabel = new JLabel("High Score: " + Player.getHighScore());

        // Create title label
        titleLabel = new JLabel("Snake Game");

        // Set font and color
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.BLUE);

        // Create buttons
        singlePlayerButton = new JButton("Single Player");
        doublePlayerButton = new JButton("Double Player");
        leaderboardButton = new JButton("Leaderboard");

        // Set preferred button size
        Dimension buttonSize = new Dimension(200, 50);
        singlePlayerButton.setPreferredSize(buttonSize);
        doublePlayerButton.setPreferredSize(buttonSize);
        leaderboardButton.setPreferredSize(buttonSize);

        // Add action listeners to buttons
        singlePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game snakeGame = new Game();

                // Switch to game panel
                JFrame window = (JFrame) SwingUtilities.getWindowAncestor(GameMenu.this);
                window.getContentPane().removeAll();
                window.getContentPane().add(snakeGame);
                window.pack();
                snakeGame.requestFocus();
                window.setLocationRelativeTo(null);
            }
        });

        doublePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DoubleGame snakeGame = new DoubleGame();

                // Switch to game panel
                JFrame window = (JFrame) SwingUtilities.getWindowAncestor(GameMenu.this);
                window.getContentPane().removeAll();
                window.getContentPane().add(snakeGame);
                window.pack();
                snakeGame.requestFocus();
                window.setLocationRelativeTo(null);
            }
        });

        leaderboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LeaderBoard leaderBoard = new LeaderBoard(GameConfig.BOARD_WIDTH, GameConfig.BOARD_HEIGHT);

                // Switch to leaderboard panel
                JFrame window = (JFrame) SwingUtilities.getWindowAncestor(GameMenu.this);
                window.getContentPane().removeAll();
                window.getContentPane().add(leaderBoard);
                window.pack();
                leaderBoard.requestFocus();
                window.setLocationRelativeTo(null);
            }
        });

        // Create constraints for the buttons
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(10, 0, 0, 0);

        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 0;
        titleConstraints.insets = new Insets(0, 0, 20, 0);

        GridBagConstraints highScoreConstraints = new GridBagConstraints();
        highScoreConstraints.gridx = 0;
        highScoreConstraints.gridy = 0;
        highScoreConstraints.insets = new Insets(30, 0, 0, 0);

        // Add high score to panel
        add(highScoreLabel, highScoreConstraints);

        // Add title to panel
        add(titleLabel, titleConstraints);

        // Add buttons to panel
        add(singlePlayerButton, constraints);
        add(doublePlayerButton, constraints);
        add(leaderboardButton, constraints);

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
    }

}