package gui;

import controller.NineMensMorrisGame;
import controller.NineMensMorrisGame.Cells;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NineMensMorrisGUI extends JFrame {
    public static final int CELL_SIZE = 77;
    public static final int TOTAL_PIECES = 9;
    public static final String PATH = System.getProperty("user.dir") + "\\src\\assets\\img\\Piece_";

    private boolean state = false;

    private JLabel gameStatusBar;

    private GameBoard gameboard;

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
        gameboard = new GameBoard();
        gameboard.setPreferredSize(new Dimension(512, 576));

        InitPieces whitePieces = new InitPieces("White");
        whitePieces.setPreferredSize(new Dimension(64,64*TOTAL_PIECES));

        InitPieces blackPieces = new InitPieces("Black");
        blackPieces.setPreferredSize(new Dimension(64, 64*TOTAL_PIECES));

        TurnBar barStatus = new TurnBar();
        barStatus.setPreferredSize(new Dimension(512, 90));

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(Color.decode("#A9814E"));

        contentPane.add(gameboard, BorderLayout.CENTER);
        contentPane.add(whitePieces, BorderLayout.WEST);
        contentPane.add(blackPieces, BorderLayout.EAST);
        contentPane.add(barStatus, BorderLayout.SOUTH);
    }

    class InitPieces extends JPanel{
        JLabel[] piecesList = new JLabel[TOTAL_PIECES];

        InitPieces(String color){
            setBackground(null);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            for(int i = 0; i < TOTAL_PIECES; i++){
                piecesList[i] = new JLabel();
                piecesList[i].setHorizontalAlignment(JLabel.CENTER);
                piecesList[i].setHorizontalAlignment(JLabel.CENTER);
                piecesList[i].setIcon(new ImageIcon(PATH + color + ".png"));
                int finalI = i;
                piecesList[i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(state){
                            if(piecesList[finalI].getIcon().toString().equalsIgnoreCase(PATH + color + "_Selected.png")){
                                piecesList[finalI].setIcon(new ImageIcon(PATH + color + ".png"));
                                state = false;
                            }
                        }else{
                            piecesList[finalI].setIcon(new ImageIcon(PATH + color + "_Selected.png"));
                            state = true;
                        }
                    }
                });
                add(piecesList[i]);
            }
        }
    }

    class GameBoard extends JPanel {
        JLabel gameBoardBg = new JLabel();

        GameBoard() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int rowSelected = e.getY() / CELL_SIZE;
                    int colSelected = e.getX() / CELL_SIZE;

                    if(state){
                        System.out.println("Deberia colocar una ficha en esta casilla: (" + rowSelected + ", " + colSelected + ")");
                    }else{
                        System.out.println("No hay ficha seleccionada");
                    }
                }
            });

            gameBoardBg.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\src\\assets\\img\\GameBoard_S.png"));
            gameBoardBg.setOpaque(true);
            gameBoardBg.setBackground(null);
            setBackground(Color.decode("#A9814E"));
            setLayout(new BorderLayout());

            gameBoardBg.setHorizontalAlignment(JLabel.CENTER);
            gameBoardBg.setVerticalAlignment(JLabel.CENTER);
            add(gameBoardBg, BorderLayout.CENTER);


        }
    }

    class TurnBar extends JPanel{
        JLabel gameStatusBar = new JLabel();
        JLabel activePieceIcon = new JLabel();

        TurnBar(){

            setLayout(new FlowLayout());
            setBackground(null);

            activePieceIcon.setIcon(new ImageIcon(PATH +"White"+ ".png"));
            activePieceIcon.setVerticalAlignment(JLabel.NORTH);
            activePieceIcon.setHorizontalAlignment(JLabel.CENTER);
            add(activePieceIcon, BorderLayout.WEST);

            gameStatusBar.setText("TURN COLOR");
            gameStatusBar.setHorizontalAlignment(JLabel.CENTER);
            gameStatusBar.setVerticalAlignment(JLabel.CENTER);
            add(gameStatusBar, BorderLayout.CENTER);
        }
    }


}
