import javax.swing.*;

import config.GameConfig;
import game.GameMenu;

public class App {
    public static void main(String[] args) throws Exception {

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
