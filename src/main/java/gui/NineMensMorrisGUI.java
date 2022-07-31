package gui;

import controller.NineMensMorrisGame;
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

    //Lista de Piezas para la fase de colocación y posteriormente para indicar las fichas eliminadas
    private Piece[] piecesOnLeft;
    private Piece[] piecesOnRight;

    //Label para mostrar la ficha que corresponde al turno actual
    private JLabel activePieceIcon;

    //Controlador que se encarga de la lógica del tablero en general
    private final NineMensMorrisGame controller;

    //Instancias de los jugadores
    private static Player currentPlayer;
    private static Player rivalPlayer;

    //Matriz de tipo Cell que serán contenedores para los objetos de tipo Piece
    private Cell[][] casillas;

    //tabla de vecinos para molino
    /*public int[][] NeighrbordsMill1 ={  {3,6,3,6},{1,2,0,6},{3,6,0,3},

                                        {3,5,3,5},{0,2,1,5},{3,5,1,3},

                                        {3,4,3,4},{0,1,2,4},{3,4,2,3},

                                        {0,6,1,2},{1,5,0,2},{2,4,0,1},

                                        {2,4,5,6},{1,5,4,6},{0,6,4,5},

                                        {2,3,3,4},{5,6,2,4},{2,3,2,3},

                                        {1,3,3,5},{4,6,1,5},{1,3,1,3},

                                        {0,3,3,6},{4,5,0,6},{0,3,0,3}};*/

    //Arreglo que contiene los x e y de los vecinos de cada casilla
    public int[] NeighrbordsMill1 ={  3,6,3,6,1,2,0,6,3,6,0,3,
                                      3,5,3,5,0,2,1,5,3,5,1,3,
                                      3,4,3,4,0,1,2,4,3,4,2,3,
                                      0,6,1,2,1,5,0,2,2,4,0,1,
                                      2,4,5,6,1,5,4,6,0,6,4,5,
                                      2,3,3,4,5,6,2,4,2,3,2,3,
                                      1,3,3,5,4,6,1,5,1,3,1,3,
                                      0,3,3,6,4,5,0,6,0,3,0,3};

    //Constructor para partida jugador contra jugador
    public NineMensMorrisGUI() {
        this(new NineMensMorrisGame(false));
    }

    //Constructor general (puede aceptar el caso jugador contra bot)
    public NineMensMorrisGUI(NineMensMorrisGame controller) {
        this.controller = controller;
        setContentPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setTitle("Nine Men's Morris");
        setVisible(true);
    }

    //Inicialización de los componentes de la ventana del programa
    private void setContentPane() {
        //Se dimensiona el tablero de juego
        GameBoard gameboard = new GameBoard();
        gameboard.setPreferredSize(new Dimension(504, 600));

        //Se inicializa el panel izquierdo para las piezas (Blancas)
        piecesOnLeft = new Piece[TOTAL_PIECES];
        InitPieces whitePieces = new InitPieces("White", piecesOnLeft);
        //Se pone como seleccionado la primera ficha para indicar que este inicia la partida
        piecesOnLeft[0].toggleSelected();

        //Se inicializa el panel derecho para las piezas (Negras)
        piecesOnRight = new Piece[TOTAL_PIECES];
        InitPieces blackPieces = new InitPieces("Black", piecesOnRight);

        //Se crea un contenedor para los paneles
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(Color.decode("#A9814E"));

        //Distribución de paneles
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

            casillas = controller.getTable();


            //Añade al layout las casillas que contendrán las piezas
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


            int k =0;
            for(int i =0;i<casillas.length;i++){
                for(int j=0;j<casillas.length;j++){
                    if(casillas[i][j] != null){
                        int[] arreglo = new int[4];
                        while(k<96){
                            for(int l=0;l<arreglo.length;l++){
                                arreglo[l] = NeighrbordsMill1[k];
                                k++;
                            }
                            break;
                        }
                        casillas[i][j].setNeighbors(arreglo);
                   }
                }
            }

            /*for(int i =0;i<casillas.length;i++){
                for(int j=0;j<casillas.length;j++) {
                    if(casillas[i][j] != null) {
                        int[] prueba = casillas[i][j].getNeighbords();
                        for(int p=0; p<prueba.length;p++){
                            System.out.printf(prueba[p]+" ");;
                        }
                        System.out.println(" ");
                    }

                }

            }*/



            //Se define las dimensiones de la barra de turno y se añade al layout
            barStatus.setPreferredSize(new Dimension(512, 90));
            add(barStatus);

            //Listener del tablero para capturar las coordenadas de donde se hace clic y saber a qué casilla corresponde
            //Está lógica está limitada en su mayoría para el juego jugador contra jugador
            gameBoardBg.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("TURNO ES: " + NineMensMorrisGame.getTurn());
                    int colSelected = (e.getX()) / Cell.CELL_SIZE;
                    int rowSelected = (e.getY() - Cell.HEIGHT_PADDING) / Cell.CELL_SIZE;

                    //Imprime la posición donde se hace clic
                    System.out.println("(x,y): (" + e.getX() + ", " + e.getY() + ")");
                    System.out.println("Casilla: (" + colSelected + ", " + rowSelected + ")");

                    //El tablero tiene un padding superior, por lo que se asegura que se esté haciendo clic
                    //dentro del tablero
                    if(e.getY() <= Cell.HEIGHT_PADDING){
                        System.out.println("No estoy dentro");
                        return;
                    }

                    //Se asigna los jugadores dependiendo del turno
                    currentPlayer = controller.getCurrentPlayer();
                    rivalPlayer = controller.getRivalPlayer();

                    //Comprueba si la fase de colocar fichas todavía no ha terminado
                    if(NineMensMorrisGame.getGameState() == GameState.INIT){
                        //Si la casilla seleccionada no es null, se coloca la ficha correspondiente del turno
                        if(controller.getCell(rowSelected, colSelected) != null && !currentPlayer.isBot()) {
                            System.out.println("Debería colocar una ficha en esta casilla: (" + colSelected
                                    + ", " + rowSelected + ")");

                            //Quita una ficha de los labels iniciales
                            if(currentPlayer.getColor().equals("White")) toggleInitPieces(piecesOnLeft, piecesOnRight);
                            else toggleInitPieces(piecesOnRight, piecesOnLeft);

                            //Y añade dicha ficha al tablero
                            controller.setCell(rowSelected, colSelected);
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
                        //Si la celda seleccionada es null, no se hace nada
                        else if(controller.getCell(rowSelected, colSelected) == null)
                            System.out.println("No puedo colocar la ficha en esta casilla: (" + colSelected + ", "
                                    + rowSelected + "), no es una posición valida");
                    }

                    //Etapa de selección y movimiento de fichas
                    if(NineMensMorrisGame.getGameState() == GameState.PLAYING){
                        //Movimiento
                        //Si hay una ficha seleccionada
                        if(currentPlayer.hasPieceSelected()){
                            //Y la celda seleccionada no es null
                            if(controller.getCell(rowSelected, colSelected) != null){
                                //Se guarda la posición previa de la última ficha seleccionada.
                                int prevPositionX = currentPlayer.getSelectedPiece().getPositionX();
                                int prevPositionY = currentPlayer.getSelectedPiece().getPositionY();

                                //Si la casilla selecciona es vecina con respecto al anterior
                                if(controller.isNeighbour(prevPositionY, prevPositionX, rowSelected, colSelected)){
                                    //Y es un movimiento válido
                                    if(controller.makeMove(prevPositionY, prevPositionX, rowSelected, colSelected)){
                                        //Se mueve la ficha a la nueva casilla
                                        casillas[colSelected][rowSelected].setPiece(currentPlayer.getSelectedPiece());
                                        currentPlayer.toggleSelectedPiece();
                                        //Y se libera la casilla anterior donde estaba la ficha
                                        casillas[prevPositionX][prevPositionY].removePiece();

                                        //Se llama a la función moveMakeMill para saber si el movimiento realizado
                                        //formó un molino y retorne las posiciones de las fichas que la conforman
                                        boolean positions = controller.moveMakeMill(currentPlayer,casillas,rowSelected,colSelected);
                                        //if(!positions.isEmpty())
                                            //showMill(positions);

                                        //Se cambia el turno y se actualiza el estado del turno
                                        controller.toggleTurn();
                                        barStatus.updateTurnBar();
                                    }
                                } else {
                                    System.out.println("La casilla (" + colSelected +
                                            ", " + rowSelected + ") no es su vecino");
                                }
                            }
                        }
                    }
                }
            });

            //Imagen del tablero
            gameBoardBg.setIcon(new ImageIcon(System.getProperty("user.dir") +
                    "\\src\\main\\resources\\img\\GameBoard.png"));
            gameBoardBg.setOpaque(true);

            //Propiedades del label
            gameBoardBg.setHorizontalAlignment(JLabel.CENTER);
            gameBoardBg.setBackground(null);
            gameBoardBg.setBorder(new EmptyBorder(Cell.HEIGHT_PADDING,0,0,0));
            add(gameBoardBg, BorderLayout.NORTH);
        }

        //Función para la fase de colocación de fichas, trabaja las labels de la derecha e izquierda
        private void toggleInitPieces(Piece[] currentLabel, Piece[] rivalLabel){
            //Se toma el número de fichas de cada jugador
            int currentIndex = currentPlayer.getNumberOfPiecesOnBoard();
            int rivalIndex = rivalPlayer.getNumberOfPiecesOnBoard();
            //Y se deshabilita la ficha ya colocada en el label correspondiente del jugador actual
            currentLabel[currentIndex].toggleEnabled();
            //Si el rival todavía tiene fichas para colocar y no es bot, se selecciona la ficha superior del label
            if(rivalIndex < TOTAL_PIECES && !rivalPlayer.isBot())
                rivalLabel[rivalIndex].toggleSelected();
            //Si el rival es bot, se ignora el paso de selección y se pasa la selección al jugador
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
