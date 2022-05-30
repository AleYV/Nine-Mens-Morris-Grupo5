package gui;

import controller.NineMensMorrisGame;
import controller.NineMensMorrisGame.Cells;
import controller.NineMensMorrisGame.GameState;

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
    private static final String MILL = "_Mill.png";
    private static final ImageIcon WHITEICON = new ImageIcon(PATH + "White.png");
    private static final ImageIcon WHITESELECTEDICON = new ImageIcon(PATH + "White" + SELECTED);
    private static final ImageIcon BLACKICON = new ImageIcon(PATH + "Black.png");
    private static final ImageIcon BLACKSELECTEDICON = new ImageIcon(PATH + "Black" + SELECTED);
    private static final ImageIcon WHITEMILLICON = new ImageIcon(PATH + "White" + MILL);
    private static final ImageIcon BLACKMILLICON = new ImageIcon(PATH + "Black" + MILL);

    private boolean selected = false;
    private int indexW = 0;
    private int indexB = 0;
    private int autoMill = 0;
    private int prevPositionX = 0;
    private int prevPositionY = 0;

    private JLabel[] whitePieces;
    private JLabel[] blackPieces;

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
        GameBoard gameboard = new GameBoard();
        gameboard.setPreferredSize(new Dimension(504, 600));

        whitePieces = new JLabel[TOTAL_PIECES];
        InitPieces whitePieces = new InitPieces("White",this.whitePieces);
        whitePieces.piecesList[0].setIcon(WHITESELECTEDICON);
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
        }
    }

    class GameBoard extends JPanel {
        JLabel gameBoardBg = new JLabel();
        JLabel[][] cellsAvailable = new JLabel[7][7];

        GameBoard() {
            setBackground(Color.decode("#A9814E"));
            setLayout(new BorderLayout());

            for (int i = -3; i <= 3; i++){
                for(int j = -3; j <= 3; j++){
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
                    System.out.println("TURNO ES: " + controller.getTurn());
                    int colSelected = (e.getX()) / CELL_SIZE;
                    int rowSelected = (e.getY() - HEIGHT_PADDING) / CELL_SIZE;

                    //Imprime la posición donde se hace click
                    System.out.println("(x,y): (" + e.getX() + ", " + e.getY() + ")");
                    System.out.println("Casilla: (" + colSelected + ", " + rowSelected + ")");

                    //Se asegura que se esta clickeando dentro del tablero
                    if(e.getY() <= HEIGHT_PADDING){
                        System.out.println("No estoy dentro");
                        return;
                    }

                    //Comprueba si la fase de colocar fichas todavía no ha terminado
                    if(controller.getGameState() == GameState.INIT){
                        //Si la casilla seleccionada esta vacía, se coloca la ficha correspondiente del turno
                        if(controller.getCell(rowSelected, colSelected) == Cells.EMPTY) {
                            System.out.println("Deberia colocar una ficha en esta casilla: (" + colSelected + ", " + rowSelected + ")");

                            //Si el turno es de las fichas blancas, y aún quedan fichas negras, coloca la ficha
                            //en la casilla seleccionada y pasa turno
                            if(controller.getTurn()){
                                whitePieces[indexW++].setEnabled(false);
                                if(indexB < TOTAL_PIECES) blackPieces[indexB].setIcon(BLACKSELECTEDICON);
                                cellsAvailable[colSelected][rowSelected].setIcon(WHITEICON);
                            }
                            //Si el turno es de las fichas negras, y aún quedan fichas blancas, coloca la ficha
                            //en la casilla seleccionada y pasa turno
                            else{
                                blackPieces[indexB++].setEnabled(false);
                                if(indexW < TOTAL_PIECES) whitePieces[indexW].setIcon(WHITESELECTEDICON);
                                cellsAvailable[colSelected][rowSelected].setIcon(BLACKICON);
                            }
                            controller.setCell(rowSelected, colSelected);
                            barStatus.updateTurnBar();

                            //Si ambos jugadores ya colocaron sus fichas, se cambia el estado del juego, culminando
                            //la fase de colocación
                            if(indexW == TOTAL_PIECES && indexB == TOTAL_PIECES){
                                indexB = indexW = 0;
                                controller.setGameState(GameState.PLAYING);
                                System.out.println("Culmino la fase de inicio.");
                            }
                        }
                        //Si la celda seleccionada no pertenece a una casilla válida, no se hace nada
                        else if(controller.getCell(rowSelected, colSelected) == Cells.DISABLED)
                            System.out.println("No puedo colocar la ficha en esta casilla: (" + colSelected + ", "
                                    + rowSelected + "), no es una posicion valida");
                        else
                            System.out.println("No puedo colocar la ficha en esta casilla: (" + colSelected + ", "
                                    + rowSelected + "), esta ocupada");
                    }

                    //Etapa de seleccion y movimiento de fichas
                    if(controller.getGameState() == GameState.PLAYING){
                        //Movimiento
                        //Si hay una ficha seleccionada
                        if(selected){
                            //Y la celda seleccionada está vacía
                            if(controller.getCell(rowSelected, colSelected) == Cells.EMPTY){
                                //Se llama a la función del controlador para comprobar si es un movimiento válido
                                if(controller.isNeighbour(prevPositionY, prevPositionX, rowSelected, colSelected)){
                                    //Se llama a la función del controlador para comprobar si es un movimiento válido
                                    if(controller.makeMove(prevPositionY, prevPositionX, rowSelected, colSelected)){
                                        //Se libera la casilla donde estaba la ficha
                                        cellsAvailable[prevPositionX][prevPositionY].setIcon(null);
                                        //Y se coloca la ficha en la casilla seleccionada
                                        if(controller.getTurn())
                                            cellsAvailable[colSelected][rowSelected].setIcon(WHITEICON);
                                        else
                                            cellsAvailable[colSelected][rowSelected].setIcon(BLACKICON);
/*
                                        //Llama a la funcion moveMakeMill para saber si el movimiento realizado
                                        //formó un molino y retorne las posiciones de la fichas que la conforman
                                        String positions = controller.moveMakeMill();
                                        if(!positions.isEmpty())
                                            showMill(positions);
                                        else if(autoMill++ == 10) {
                                            showMill("003060");
                                            controller.setTurn(false);
                                            JOptionPane.showMessageDialog(getParent(), "DEBUG. Se auto formado el molino, juego terminado");
                                            controller.setGameState(GameState.DRAW);
                                        }
*/
                                        selected = !selected;
                                        controller.setTurn(!controller.getTurn());
                                        barStatus.updateTurnBar();
                                    }
                                } else{
                                    System.out.println("La casilla ("+rowSelected+","+colSelected+") no es su vecino");
                                }
                            }
                            //Si la ficha ya estaba seleccionada, se deselecciona y permite al jugador escoger otra
                            else if(cellsAvailable[colSelected][rowSelected]!=null){
                                if(controller.getCell(rowSelected, colSelected) == Cells.WHITE ||
                                        controller.getCell(rowSelected, colSelected) == Cells.BLACK){
                                    System.out.println("La casilla esta ocupada");
                                }
                                if(cellsAvailable[colSelected][rowSelected].getIcon().toString().contains(SELECTED)){
                                    if(controller.getTurn())
                                        selectPiece(colSelected, rowSelected, "White");
                                    else
                                        selectPiece(colSelected, rowSelected, "Black");
                                    prevPositionX = prevPositionY = -1;
                                }
                            }
                        }
                        //Selección
                        else{
                            //Si es turno de las blancas y se escoge una ficha blanca, esta ficha se selecciona
                            if(controller.getTurn() && controller.getCell(rowSelected, colSelected) == Cells.WHITE) {
                                selectPiece(colSelected, rowSelected, "White");
                                prevPositionX = colSelected;
                                prevPositionY = rowSelected;
                            }
                            //Si es turno de las negras y se escoge una ficha negra, esta ficha se selecciona
                            else if(!controller.getTurn() && controller.getCell(rowSelected, colSelected) == Cells.BLACK) {
                                selectPiece(colSelected, rowSelected, "Black");
                                prevPositionX = colSelected;
                                prevPositionY = rowSelected;
                            }
                        }
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

        private void selectPiece(int i, int j, String color){
            if(selected){
                if(cellsAvailable[i][j].getIcon().toString().contains(SELECTED)) {
                    cellsAvailable[i][j].setIcon(new ImageIcon(PATH + color + ".png"));
                    selected = !selected;
                }
            }else{
                cellsAvailable[i][j].setIcon(new ImageIcon(PATH + color + SELECTED));
                selected = !selected;
            }
        }

        private void showMill(String positions){

            int c0 = Character.getNumericValue(positions.charAt(0));
            int c1 = Character.getNumericValue(positions.charAt(1));
            int c2 = Character.getNumericValue(positions.charAt(2));
            int c3 = Character.getNumericValue(positions.charAt(3));
            int c4 = Character.getNumericValue(positions.charAt(4));
            int c5 = Character.getNumericValue(positions.charAt(5));

            if(controller.getTurn()) {
                cellsAvailable[c0][c1].setIcon(WHITEMILLICON);
                cellsAvailable[c2][c3].setIcon(WHITEMILLICON);
                cellsAvailable[c4][c5].setIcon(WHITEMILLICON);
            }else{
                cellsAvailable[c0][c1].setIcon(BLACKMILLICON);
                cellsAvailable[c2][c3].setIcon(BLACKMILLICON);
                cellsAvailable[c4][c5].setIcon(BLACKMILLICON);
            }
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

        public void updateTurnBar(){
            if(controller.getTurn())
                activePieceIcon.setIcon(WHITEICON);
            else
                activePieceIcon.setIcon(BLACKICON);
        }
    }
}
