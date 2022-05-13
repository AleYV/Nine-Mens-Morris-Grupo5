package gui;

import controller.NineMensMorrisGame;
import controller.NineMensMorrisGame.Cells;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NineMensMorrisGUI extends JFrame {
    public static final int CELL_SIZE = 72;
    public static final int TOTAL_PIECES = 9;
    public static final int HEIGHT_PADDING = 16;
    private static final String PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\img\\Piece_";
    private static final String SELECTED = "_Selected.png";
    private static final ImageIcon WHITEICON = new ImageIcon(PATH + "White.png");
    private static final ImageIcon WHITESELECTEDICON = new ImageIcon(PATH + "White" + SELECTED);
    private static final ImageIcon BLACKICON = new ImageIcon(PATH + "Black.png");
    private static final ImageIcon BLACKSELECTEDICON = new ImageIcon(PATH + "Black" + SELECTED);

    private boolean state = true;
    private int indexW = 0;
    private int indexB = 0;

    private JLabel[] whitePieces;
    private JLabel[] blackPieces;

    private GameBoard gameboard;
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
        gameboard.setPreferredSize(new Dimension(504, 600));

        whitePieces = new JLabel[TOTAL_PIECES];
        InitPieces whitePieces = new InitPieces("White",this.whitePieces);
        whitePieces.setPreferredSize(new Dimension(64,64*TOTAL_PIECES));

        blackPieces = new JLabel[TOTAL_PIECES];
        InitPieces blackPieces = new InitPieces("Black", this.blackPieces);
        blackPieces.setPreferredSize(new Dimension(64, 64*TOTAL_PIECES));

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(Color.decode("#A9814E"));

        contentPane.add(gameboard, BorderLayout.CENTER);
        contentPane.add(whitePieces, BorderLayout.WEST);
        contentPane.add(blackPieces, BorderLayout.EAST);
    }

    //Muestra los dos paneles con las piezas blancas y negras a los lados
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
        JLabel[][] cellsAvailable = new JLabel[7][7];

        GameBoard() {
            setBackground(Color.decode("#A9814E"));
            setLayout(new BorderLayout());

            for (int i=-3; i<=3; i++){
                for(int j=-3;j<=3;j++){
                    if (i==0 && j==0) continue;

                    if(i == 0) {
                        initCellsOnBoard(3, (j + 3));
                        continue;
                    }
                    if(j == 0) {
                        initCellsOnBoard((i + 3), 3);
                        continue;
                    }
                    if(Math.abs(i) == Math.abs(j))
                        initCellsOnBoard((i + 3), (j + 3));
                }
            }

            TurnBar barStatus = new TurnBar();
            barStatus.setPreferredSize(new Dimension(512, 90));
            add(barStatus);

            gameBoardBg.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int rowSelected = (e.getY() - HEIGHT_PADDING) / CELL_SIZE;
                    int colSelected = (e.getX()) / CELL_SIZE;

                    //Imprime la posición donde se hace click
                    System.out.println("(x,y): (" + e.getX() + ", " + e.getY() + ")");
                    System.out.println("Casilla: (" + colSelected + ", " + rowSelected + ")");

                    //Se asegura que se esta clickeando dentro del tablero
                    if(e.getY() <= HEIGHT_PADDING){
                        System.out.println("No estoy dentro");
                        return;
                    }

                    //Comprueba si la fase de colocar fichas todavía no ha terminado
                    if(state){
                        //Si la casilla seleccionada esta vacía, se coloca la ficha correspondiente del turno
                        if(controller.getCell(rowSelected, colSelected) == Cells.EMPTY) {
                            System.out.println("Deberia colocar una ficha en esta casilla: (" + colSelected + ", " + rowSelected + ")");

                            // Si el turno es de las fichas blancas, y aún hay fichas en ambos lados, coloca la ficha en la casilla seleccionada
                            // y pasa turno
                            if(controller.getTurn() == 'W' && indexW < TOTAL_PIECES && indexB < TOTAL_PIECES){
                                whitePieces[indexW++].setEnabled(false);
                                blackPieces[indexB].setIcon(BLACKSELECTEDICON);
                                activePieceIcon.setIcon(BLACKICON);
                                cellsAvailable[colSelected][rowSelected].setIcon(WHITEICON);
                            }
                            //Sino, comprueba si hay fichas en ambos lados y coloca la ficha negra en la casilla seleccionada
                            //y pasa turno
                            else if (indexB < TOTAL_PIECES && indexW < TOTAL_PIECES){
                                blackPieces[indexB++].setEnabled(false);
                                whitePieces[indexW].setIcon(WHITESELECTEDICON);
                                activePieceIcon.setIcon(WHITEICON);
                                cellsAvailable[colSelected][rowSelected].setIcon(BLACKICON);
                            }
                            //Cuando solo queda unicamente una ficha, coloca la ficha negra en la casilla seleccionada
                            //y se termina la fase de colocación
                            else{
                                blackPieces[indexB].setEnabled(false);
                                activePieceIcon.setIcon(WHITEICON);
                                cellsAvailable[colSelected][rowSelected].setIcon(new ImageIcon(PATH + "Black.png"));
                                state = false;
                            }
                            controller.setCell(rowSelected, colSelected);
                        }
                        //Si la celda seleccionada no pertenece a una casilla válida, no se hace nada
                        else if(controller.getCell(rowSelected, colSelected) == Cells.DISABLED)
                            System.out.println("No puedo colocar la ficha en esta casilla: (" + colSelected + ", "
                                    + rowSelected + "), no es una posicion valida");
                        else
                            System.out.println("No puedo colocar la ficha en esta casilla: (" + colSelected + ", "
                                    + rowSelected + "), esta ocupada");
                    }else{
                        System.out.println("Culmino la fase de inicio.");
                    }
                }
            });

            gameBoardBg.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\resources\\img\\GameBoard.png"));
            gameBoardBg.setOpaque(true);

            gameBoardBg.setHorizontalAlignment(JLabel.CENTER);
            gameBoardBg.setBackground(null);
            gameBoardBg.setBorder(new EmptyBorder(HEIGHT_PADDING,0,0,0));
            add(gameBoardBg, BorderLayout.NORTH);
        }

        private void initCellsOnBoard(int i, int j){
            cellsAvailable[i][j] = new JLabel();
            cellsAvailable[i][j].setBounds(CELL_SIZE*i, (CELL_SIZE*j + HEIGHT_PADDING), CELL_SIZE, CELL_SIZE);
            cellsAvailable[i][j].setHorizontalAlignment(JLabel.CENTER);
            add(cellsAvailable[i][j]);
        }
    }

    //Muestra el panel con el turno y su color de ficha correspondiente al turno
    class TurnBar extends JPanel{
        JLabel gameStatusBar = new JLabel();

        TurnBar(){
            setLayout(new FlowLayout());
            setBackground(null);

            activePieceIcon = new JLabel();
            activePieceIcon.setIcon(WHITEICON);

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
