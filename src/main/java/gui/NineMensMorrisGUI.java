package gui;

import controller.NineMensMorrisGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NineMensMorrisGUI extends JFrame {
    public static final int CELL_SIZE = 77;
    public static final int CELL_PADDING = CELL_SIZE / 6;

    private JLabel gameStatusBar;
    private JLabel whitePiecesStart;
    private JLabel blackPiecesStart;

    private Gameboard gameboard;

    private NineMensMorrisGame controller;

    public NineMensMorrisGUI() {
        this(new NineMensMorrisGame());
    }

    public NineMensMorrisGUI(NineMensMorrisGame controller) {
        this.controller = controller;
        setContentPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setTitle("Nine Men's Morris");
        setVisible(true);
    }

    private void setContentPane() {
        gameboard = new Gameboard();
        gameboard.setPreferredSize(new Dimension(650, 600));

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(Color.decode("#A9814E"));

        whitePiecesStart = new JLabel();
        whitePiecesStart.setText("WHITE PIECES");
        whitePiecesStart.setHorizontalAlignment(JLabel.CENTER);
        whitePiecesStart.setBackground(Color.WHITE);
        whitePiecesStart.setOpaque(true);
        contentPane.add(whitePiecesStart, BorderLayout.WEST);

        contentPane.add(gameboard, BorderLayout.CENTER);

        blackPiecesStart = new JLabel();
        blackPiecesStart.setText("BLACK PIECES");
        blackPiecesStart.setHorizontalAlignment(JLabel.CENTER);
        blackPiecesStart.setBackground(Color.GRAY);
        blackPiecesStart.setOpaque(true);
        contentPane.add(blackPiecesStart, BorderLayout.EAST);


        //contentPane.add(gameStatusBar, BorderLayout.SOUTH);

    }

    class Gameboard extends JPanel {

        JLabel gameboardBg = new JLabel();

        Gameboard() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Hice click en: (" + e.getX() + ", " + e.getY() + ")");
                }
            });

            gameboardBg.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\src\\assets\\img\\GameBoard_S.png"));
            gameboardBg.setOpaque(true);
            gameboardBg.setBackground(null);
            setBackground(Color.decode("#A9814E"));
            setLayout(new BorderLayout());
            gameboardBg.setHorizontalAlignment(JLabel.CENTER);
            gameboardBg.setVerticalAlignment(JLabel.CENTER);
            add(gameboardBg, BorderLayout.CENTER);
            gameStatusBar = new JLabel();
            gameStatusBar.setText("STATUS BAR");
            gameStatusBar.setBackground(Color.CYAN);
            gameStatusBar.setOpaque(true);
            gameStatusBar.setHorizontalAlignment(JLabel.CENTER);
            add(gameStatusBar, BorderLayout.SOUTH);
        }
    }
}
