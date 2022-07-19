package model;

import controller.NineMensMorrisGame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Player{

    //Número de fichas que puede tener un jugador
    private static final int TOTAL_PIECES = 9;
    //Número de fichas que el jugador tiene en el tablero
    private int piecesOnBoard = 0;
    //Lista de fichas que tiene el jugador
    private final Piece[] listOfPieces = new Piece[TOTAL_PIECES];
    //¿El jugador tiene una ficha ya seleccionada?
    private boolean hasPieceSelected = false;

    //¿Soy un bot?
    private boolean iAmBot = false;

    //Indice de la ficha seleccionada con respecto a la lista
    private int indexPieceSelected = -1;

    //Color de las fichas del jugador
    private final String color;

    //Constructor que acepta un String como argumento para el color de ficha
    public Player(String color) {
        this.color = color;
    }

    //Constructor que acepta un String como argumento para el color de ficha y si el jugador es un bot
    public Player(String color, boolean iAmBot){
        this.color = color;
        this.iAmBot = iAmBot;
        //Si es un bot la ficha seleccionada será la primera de la lista
        if(iAmBot){
            indexPieceSelected = 0;
        }
    }

    //Retorna el color del jugador
    public String getColor() {
        return color;
    }

    //Retorna si el jugador es un bot o no
    public boolean isBot() {
        return iAmBot;
    }

    //Incrementa el contador de fichas que están en el tablero
    public void addPieceOnBoard(){
        if(++piecesOnBoard > TOTAL_PIECES) {
            piecesOnBoard--;
            System.out.println("Todas las fichas fueron colocadas en el tablero");
        }
    }

    //Retorna el número de fichas que están en el tablero
    public int getNumberOfPiecesOnBoard(){
        return piecesOnBoard;
    }

    //Retorna la lista de fichas del jugador
    public Piece[] getListOfPieces() {
        return listOfPieces;
    }

    //Busca la ficha del jugador en un index específico de la lista
    public Piece getPieceAtIndex(int index){
        return listOfPieces[index];
    }

    //Busca la ficha que el jugador tiene en cierta posición del tablero
    public Piece getPieceAtPosition(int x, int y){
        for (Piece piece:
             listOfPieces) {
            if(piece.getPositionX() == x && piece.getPositionY() == y)
                return piece;
        }
        System.out.println("No se encontró la ficha de color " + color + " en la posición (" + x + ", " + y + ").");
        return null;
    }

    //Retorna si el jugador tiene una ficha seleccionada o no
    public boolean hasPieceSelected(){
        return hasPieceSelected;
    }

    //Retorna la ficha que el jugador ha seleccionado
    public Piece getSelectedPiece(){
        return listOfPieces[indexPieceSelected];
    }

    //Palanca: Cambia el estado de la ficha ha seleccionado o deseleccionado
    public void toggleSelectedPiece(){
        if(indexPieceSelected != -1) {
            listOfPieces[indexPieceSelected].toggleSelected();
            hasPieceSelected = !hasPieceSelected;
        }
    }

    //Inicializa la lista de fichas del jugador
    public void setListOfPieces() {
        int i = 0;
        while (i < TOTAL_PIECES) {
            //Inicializa toda la lista con las fichas del color del jugador y las posiciones en (0,0)
            listOfPieces[i] = new Piece(color, 0, 0);
            //Si el jugador no es un bot, se le añade un listener a las fichas para su selección
           if(!iAmBot){
               int finalI = i;
               listOfPieces[i].addMouseListener(new MouseAdapter() {
                   @Override
                   public void mouseClicked(MouseEvent e) {
                       //Si es mi turno y no es la fase de colocación
                       if (NineMensMorrisGame.getTurn().equals(color) && NineMensMorrisGame.getGameState() != NineMensMorrisGame.GameState.INIT) {
                           //Compruebo si hay ficha seleccionada
                           if (hasPieceSelected) {//Si vuelvo a seleccionar una ficha ya seleccionada, se deselecciona
                               if (listOfPieces[finalI].isSelected()) {
                                   listOfPieces[finalI].toggleSelected();
                                   hasPieceSelected = listOfPieces[finalI].isSelected();
                                   indexPieceSelected = -1;
                                   System.out.println("Deseleccione la pieza " + finalI);
                               } else {
                                   System.out.println("No soy la pieza seleccionada, pero mi index es: " + finalI);
                               }
                           } else {//Si no hay, puedo seleccionar una
                               listOfPieces[finalI].toggleSelected();
                               hasPieceSelected = listOfPieces[finalI].isSelected();
                               indexPieceSelected = finalI;
                               System.out.println("seleccione la pieza " + finalI);
                           }
                       } else {
                           System.out.println("No es mi turno");
                       }
                   }
               });
           }
           i++;
        }
    }
}
