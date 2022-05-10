package gui;

import controller.NineMensMorrisGame;
import controller.NineMensMorrisGame.Cells;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NineMensMorrisGUI extends JFrame {
    public static final int CELL_SIZE = 73;
    public static final int TOTAL_PIECES = 9;
    public static final int HEIGHT_PADDING = 16;
    public static final String PATH = System.getProperty("user.dir") + "\\src\\assets\\img\\Piece_";
    public static final String SELECTED = "_Selected.png";

    private boolean state = true;
    private int indexW = 0;
    private int indexB = 0;

    private JLabel[] whitePieces;
    private JLabel[] blackPieces;

    private GameBoard gameboard;
    private TurnBar barStatus;
    private JLabel activePieceIcon;

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

        whitePieces = new JLabel[TOTAL_PIECES];
        InitPieces whitePieces = new InitPieces("White",this.whitePieces);
        whitePieces.setPreferredSize(new Dimension(64,64*TOTAL_PIECES));

        blackPieces = new JLabel[TOTAL_PIECES];
        InitPieces blackPieces = new InitPieces("Black", this.blackPieces);
        blackPieces.setPreferredSize(new Dimension(64, 64*TOTAL_PIECES));

        barStatus = new TurnBar();
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

        JLabel[] piecesList;

        InitPieces(String color, JLabel[] piecesList){
            this.piecesList = piecesList;
            setBackground(null);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            for(int i = 0; i < TOTAL_PIECES; i++){
                this.piecesList[i] = new JLabel();
                this.piecesList[i].setHorizontalAlignment(JLabel.CENTER);
                this.piecesList[i].setIcon(new ImageIcon(PATH + color + ".png"));
                this.piecesList[i].setDisabledIcon(new ImageIcon(PATH + "Null.png"));
                add(this.piecesList[i]);
            }

            if(color.charAt(0) == controller.getTurn())
                this.piecesList[0].setIcon(new ImageIcon(PATH + color + SELECTED));
        }
    }

    class GameBoard extends JPanel {
        JLabel gameBoardBg = new JLabel();

        GameBoard() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    float rowSelected = (e.getY() - HEIGHT_PADDING) / (float)CELL_SIZE;
                    float colSelected = (e.getX() - HEIGHT_PADDING) / (float)CELL_SIZE;

                    System.out.println("(x,y): (" + e.getX() + ", " + e.getY() + ")");
                    System.out.println("Casilla: (" + colSelected + ", " + rowSelected + ")");

                    int row = (int)rowSelected;
                    int col = (int)colSelected;
                    //INIT TABLE
                    if(state && rowSelected >= 0 && colSelected >= 0){
                        if(controller.getCell(row, col) == Cells.EMPTY) {
                            System.out.println("Deberia colocar una ficha en esta casilla: (" + col + ", " + row + ")");

                            if(controller.getTurn() == 'W' && indexW < TOTAL_PIECES && indexB < TOTAL_PIECES){
                                whitePieces[indexW++].setEnabled(false);
                                blackPieces[indexB].setIcon(new ImageIcon(PATH + "Black" + SELECTED));
                                activePieceIcon.setIcon(new ImageIcon(PATH + "Black.png"));
                                controller.setCell((int)rowSelected, (int)colSelected);
                            }
                            else if (indexB < TOTAL_PIECES && indexW < TOTAL_PIECES){
                                blackPieces[indexB++].setEnabled(false);
                                whitePieces[indexW].setIcon(new ImageIcon(PATH + "White" + SELECTED));
                                activePieceIcon.setIcon(new ImageIcon(PATH + "White.png"));
                                controller.setCell((int)rowSelected, (int)colSelected);
                            }else{
                                blackPieces[indexB].setEnabled(false);
                                activePieceIcon.setIcon(new ImageIcon(PATH + "White.png"));
                                controller.setCell((int)rowSelected, (int)colSelected);
                            }

                            //state = false;
                        }
                        else if(controller.getCell(row, col) == Cells.DISABLED)
                            System.out.println("No puedo colocar la ficha en esta casilla: (" + col + ", "
                                    + row + "), no es una posicion valida");
                        else
                            System.out.println("No puedo colocar la ficha en esta casilla: (" + col + ", "
                                    + row + "), esta ocupada");

                    }else{
                        System.out.println("No hay ficha seleccionada");
                    }
                }
            });

            gameBoardBg.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\src\\assets\\img\\GameBoard_S.png"));
            gameBoardBg.setOpaque(true);
            setBackground(Color.decode("#A9814E"));
            setLayout(new BorderLayout());

            gameBoardBg.setHorizontalAlignment(JLabel.CENTER);
            gameBoardBg.setBackground(null);
            gameBoardBg.setBorder(new EmptyBorder(HEIGHT_PADDING,0,0,0));
            add(gameBoardBg, BorderLayout.NORTH);
        }
    }

    class TurnBar extends JPanel{
        JLabel gameStatusBar = new JLabel();

        TurnBar(){
            setLayout(new FlowLayout());
            setBackground(null);

            activePieceIcon = new JLabel();
            activePieceIcon.setIcon(new ImageIcon(PATH + "White.png"));

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
