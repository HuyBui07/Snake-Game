package game;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import config.GameConfig;
import models.TableData;

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
        String[] columnNames = { "Rank", "Name", "Score" };

        String[][] data = TableData.getPlayers();

        // Set font and color for buttons
        Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 20);
        Color buttonColor = new Color(51, 204, 255); // Light blue color
        Border buttonBorder = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.YELLOW); // Yellow border

        // Create back button
        backButton = new JButton("Back to Menu");
        backButton.setFont(buttonFont);
        backButton.setBackground(buttonColor);
        backButton.setBorder(buttonBorder);

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

        // Create a box
        Box box = Box.createHorizontalBox();

        // Add the button to the box
        box.add(backButton);

        // Add a horizontal glue
        box.add(Box.createHorizontalGlue());

        add(box);

        // Create table
        table = new JTable(data, columnNames);

        // Set table properties
        table.setFont(new Font("Arial", Font.BOLD, 18)); // Set the font
        table.setRowSelectionAllowed(false); // Disable row selection
        table.setCellSelectionEnabled(false); // Disable cell selection
        table.setShowGrid(false); // Hide the grid
        table.setRowHeight(30); // Set the row height
        // table.setFillsViewportHeight(true); // Makes the table fill the viewport
        // height
        int tableWidth = 500;
        int tableHeight = table.getRowHeight() * 11;
        table.setPreferredScrollableViewportSize(
                new Dimension(tableWidth, tableHeight)); // Set the table size

        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);

        // // Center the text in the table
        // DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        // centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        // table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        // table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        // Make the JTable non-opaque
        table.setOpaque(false);
        ((JComponent) table.getDefaultRenderer(Object.class)).setOpaque(false);

        // Custom cell renderer
        class TransparentCellRenderer extends DefaultTableCellRenderer {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBackground(new Color(getBackground().getRed(), getBackground().getGreen(), getBackground().getBlue(),
                        128)); // Semi-transparent
                setForeground(Color.white);
                return this;
            }
        }

        // Set the custom cell renderer to the table
        TransparentCellRenderer transparentRenderer = new TransparentCellRenderer();
        transparentRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, transparentRenderer);

        // Create a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(0, 0, 0, 0)); // Transparent
        scrollPane.setPreferredSize(new Dimension(tableWidth, tableHeight));

        // Get the table header
        JTableHeader header = table.getTableHeader();

        // Create a custom renderer
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(Color.BLUE); // Set the background color
        headerRenderer.setForeground(Color.WHITE);
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Set the custom renderer to the table header
        header.setDefaultRenderer(headerRenderer);
        header.setFont(buttonFont);

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
