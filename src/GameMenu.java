import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameMenu extends JPanel {
    private JButton singlePlayerButton;
    private JButton doublePlayerButton;

    public GameMenu() {
        setPreferredSize(new Dimension(GameConfig.BOARD_WIDTH, GameConfig.BOARD_HEIGHT));
        setLayout(new GridBagLayout()); // Set layout manager

        singlePlayerButton = new JButton("Single Player");
        doublePlayerButton = new JButton("Double Player");

        // Set preferred button size
        Dimension buttonSize = new Dimension(200, 50);
        singlePlayerButton.setPreferredSize(buttonSize);
        doublePlayerButton.setPreferredSize(buttonSize);

        // Add action listeners to buttons
        singlePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game snakeGame = new Game(GameConfig.BOARD_WIDTH, GameConfig.BOARD_HEIGHT);

                // Switch to game panel
                JFrame window = (JFrame) SwingUtilities.getWindowAncestor(GameMenu.this);
                window.getContentPane().removeAll();
                window.getContentPane().add(snakeGame);
                window.pack();
                snakeGame.requestFocus();
                window.setLocationRelativeTo(null);
            }
        });

        // Create constraints for the buttons
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.NONE;

        // Add buttons to panel
        add(singlePlayerButton, constraints);
        add(doublePlayerButton, constraints);

    }
}