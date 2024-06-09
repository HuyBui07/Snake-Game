package game;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import config.GameConfig;
import utils.DatabaseConnection;

import java.sql.Connection;

public class LeaderBoard extends JPanel implements ActionListener, KeyListener {

    private int boardWidth, boardHeight;
    private JTable table;
    private JButton backButton;

    public LeaderBoard(int boardWidth, int boardHeight) {

        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        addKeyListener(this);
        setFocusable(true);

        // Initialize table with dummy data
        String[] columnNames = { "Name", "Score" };

        String[][] data = {};
        try {
            Connection connection = DatabaseConnection.getConnection();
            data = DatabaseConnection.getLeaderBoard(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create back button
        backButton = new JButton("Back to Menu");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameMenu gameMenu = new GameMenu();

                // Switch to game menu panel
                JFrame window = (JFrame) SwingUtilities.getWindowAncestor(LeaderBoard.this);
                window.getContentPane().removeAll();
                window.getContentPane().add(gameMenu);
                window.pack();
                gameMenu.requestFocus();
                window.setLocationRelativeTo(null);
            }
        });

        add(Box.createVerticalGlue());
        add(backButton);
        

        // Create table
        table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setMaximumSize(new Dimension(300, 300));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());
        add(scrollPane);
        add(Box.createVerticalGlue());

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

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }

}
