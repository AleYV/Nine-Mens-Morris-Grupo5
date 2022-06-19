package gui;

import controller.NineMensMorrisGame;
import controller.NineMensMorrisGame.Cells;
import controller.NineMensMorrisGame.GameState;
import model.Cell;
import model.Piece;
import model.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NineMensMorrisGUI extends JFrame {
    public static final int TOTAL_PIECES = 9;

    private Piece[] initWhitePieces;
    private Piece[] initBlackPieces;

    private JLabel activePieceIcon;

    private final NineMensMorrisGame controller;

    private static Player currentPlayer;
    private static Player rivalPlayer;

    private Cell[][] casillas;

    public NineMensMorrisGUI() {
        this(new NineMensMorrisGame(false));
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

        initWhitePieces = new Piece[TOTAL_PIECES];
        InitPieces whitePieces = new InitPieces("White",initWhitePieces);
        initWhitePieces[0].toggleSelected();

        initBlackPieces = new Piece[TOTAL_PIECES];
        InitPieces blackPieces = new InitPieces("Black", initBlackPieces);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(Color.decode("#A9814E"));

        contentPane.add(gameboard, BorderLayout.CENTER);
        contentPane.add(whitePieces, BorderLayout.WEST);
        contentPane.add(blackPieces, BorderLayout.EAST);
    }

    //Muestra los dos paneles con las piezas blancas y negras a los lados
    static class InitPieces extends JPanel{
        InitPieces(String color, Piece[] piecesList){
            setBackground(null);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            for(int i = 0; i < TOTAL_PIECES; i++)
                add(piecesList[i] = new Piece(color, 0, 0));
        }
    }

    class GameBoard extends JPanel {
        JLabel gameBoardBg = new JLabel();
        TurnBar barStatus = new TurnBar();

        GameBoard() {
            setBackground(Color.decode("#A9814E"));
            setLayout(new BorderLayout());

            casillas = controller.getTableG();

            for (int i = -3; i <= 3; i++){
                for(int j = -3; j <= 3; j++){
                    if (i==0 && j==0) continue;

                    if(i == 0) {
                        add(casillas[3][j + 3]);
                        continue;
                    }
                    if(j == 0) {
                        add(casillas[i + 3][3]);
                        continue;
                    }
                    if(Math.abs(i) == Math.abs(j))
                        add(casillas[i + 3][j + 3]);
                }
            }

            barStatus.setPreferredSize(new Dimension(512, 90));
            add(barStatus);

            gameBoardBg.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("TURNO ES: " + NineMensMorrisGame.getTurn());
                    int colSelected = (e.getX()) / Cell.CELL_SIZE;
                    int rowSelected = (e.getY() - Cell.HEIGHT_PADDING) / Cell.CELL_SIZE;

                    //Imprime la posición donde se hace clic
                    System.out.println("(x,y): (" + e.getX() + ", " + e.getY() + ")");
                    System.out.println("Casilla: (" + colSelected + ", " + rowSelected + ")");

                    //Se asegura que se está haciendo clic dentro del tablero
                    if(e.getY() <= Cell.HEIGHT_PADDING){
                        System.out.println("No estoy dentro");
                        return;
                    }

                    //Se asigna los jugadores dependiendo del turno
                    currentPlayer = controller.getCurrentPlayer();
                    rivalPlayer = controller.getRivalPlayer();

                    //Comprueba si la fase de colocar fichas todavía no ha terminado
                    if(controller.getGameState() == GameState.INIT){
                        //Si la casilla seleccionada esta vacía, se coloca la ficha correspondiente del turno
                        if(controller.getCell(rowSelected, colSelected) == Cells.EMPTY && !currentPlayer.isBot()) {
                            System.out.println("Deberia colocar una ficha en esta casilla: (" + colSelected + ", " + rowSelected + ")");

                            //Quita una ficha de los labels iniciales
                            if(currentPlayer.getColor().equals("White")) toggleInitPieces(initWhitePieces, initBlackPieces);
                            else toggleInitPieces(initBlackPieces, initWhitePieces);

                            //Añade una ficha al tablero
                            controller.customSetCell(rowSelected, colSelected);
                            barStatus.updateTurnBar();

                            //Si ambos jugadores ya colocaron sus fichas, se cambia el estado del juego, culminando
                            //la fase de colocación
                            if(currentPlayer.getNumberOfPiecesOnBoard() == TOTAL_PIECES
                                    && rivalPlayer.getNumberOfPiecesOnBoard() == TOTAL_PIECES){
                                controller.setGameState(GameState.PLAYING);
                                System.out.println("Culmino la fase de inicio.");
                                return;
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
                        if(currentPlayer.hasPieceSelected()){
                            //Y la celda seleccionada está vacía
                            if(controller.getCell(rowSelected, colSelected) == Cells.EMPTY){
                                //Se llama a la función del controlador para comprobar si es un movimiento válido
                                int prevPositionX = currentPlayer.getSelectedPiece().getPositionX();
                                int prevPositionY = currentPlayer.getSelectedPiece().getPositionY();

                                if(controller.isNeighbour(prevPositionY, prevPositionX, rowSelected, colSelected)){
                                    //Se llama a la función del controlador para comprobar si es un movimiento válido
                                    if(controller.makeMove(prevPositionY, prevPositionX, rowSelected, colSelected)){
                                        //Se libera la casilla donde estaba la ficha
                                        casillas[colSelected][rowSelected].setPiece(currentPlayer.getSelectedPiece());
                                        currentPlayer.toggleSelectedPiece();
                                        casillas[prevPositionX][prevPositionY].removePiece();

                                        //Llama a la función moveMakeMill para saber si el movimiento realizado
                                        //formó un molino y retorne las posiciones de las fichas que la conforman
                                        String positions = controller.moveMakeMill();
                                        if(!positions.isEmpty())
                                            showMill(positions);

                                        controller.toggleTurn();
                                        barStatus.updateTurnBar();
                                    }
                                } else{
                                    System.out.println("La casilla (" + colSelected + ", " + rowSelected + ") no es su vecino");
                                }
                            }
                        }
                    }
                }
            });

            gameBoardBg.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\resources\\img\\GameBoard.png"));
            gameBoardBg.setOpaque(true);

            gameBoardBg.setHorizontalAlignment(JLabel.CENTER);
            gameBoardBg.setBackground(null);
            gameBoardBg.setBorder(new EmptyBorder(Cell.HEIGHT_PADDING,0,0,0));
            add(gameBoardBg, BorderLayout.NORTH);
        }

        private void toggleInitPieces(Piece[] currentLabel, Piece[] rivalLabel){
            int currentIndex = currentPlayer.getNumberOfPiecesOnBoard();
            int rivalIndex = rivalPlayer.getNumberOfPiecesOnBoard();
            currentLabel[currentIndex].toggleEnabled();
            if(rivalIndex < TOTAL_PIECES && !rivalPlayer.isBot())
                rivalLabel[rivalIndex].toggleSelected();
            else if(rivalPlayer.isBot()) {
                currentIndex++;
                rivalLabel[rivalIndex].toggleEnabled();
                if(currentIndex < TOTAL_PIECES)
                    currentLabel[currentIndex].toggleSelected();
            }
        }
        private void showMill(String positions){
            int c0 = Character.getNumericValue(positions.charAt(0));
            int c1 = Character.getNumericValue(positions.charAt(1));
            int c2 = Character.getNumericValue(positions.charAt(2));
            int c3 = Character.getNumericValue(positions.charAt(3));
            int c4 = Character.getNumericValue(positions.charAt(4));
            int c5 = Character.getNumericValue(positions.charAt(5));

            currentPlayer.getPieceAtPosition(c0, c1).toggleMill();
            currentPlayer.getPieceAtPosition(c2, c3).toggleMill();
            currentPlayer.getPieceAtPosition(c4, c5).toggleMill();
        }
    }

    //Muestra el panel con el turno y su color de ficha correspondiente al turno
    class TurnBar extends JPanel{
        JLabel gameStatusBar = new JLabel();

        TurnBar(){
            setLayout(new FlowLayout());
            setBackground(null);

            activePieceIcon = new JLabel();
            activePieceIcon.setIcon(controller.getCurrentPlayer().getPieceAtIndex(0).getIcon());

            activePieceIcon.setVerticalAlignment(JLabel.NORTH);
            activePieceIcon.setHorizontalAlignment(JLabel.CENTER);
            add(activePieceIcon, BorderLayout.WEST);

            gameStatusBar.setText("TURN COLOR");
            gameStatusBar.setHorizontalAlignment(JLabel.CENTER);
            gameStatusBar.setVerticalAlignment(JLabel.CENTER);
            add(gameStatusBar, BorderLayout.CENTER);
        }

        public void updateTurnBar(){
            activePieceIcon.setIcon(controller.getCurrentPlayer().getPieceAtIndex(0).getIcon());
        }
    }
}
