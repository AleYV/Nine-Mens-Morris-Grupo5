package model;

import controller.NineMensMorrisGame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Player{

    private static final int TOTAL_PIECES = 9;
    private int piecesOnBoard = 0;
    private final Piece[] listOfPieces = new Piece[9];
    private boolean hasPieceSelected = false;

    private boolean iAmBot = false;

    private int indexPieceSelected = -1;

    private final String color;

    public Player(String color) {
        this.color = color;
    }

    public Player(String color, boolean iAmBot){
        this.color = color;
        this.iAmBot = iAmBot;
        if(iAmBot){
            indexPieceSelected = 0;
        }
    }

    public String getColor() {
        return color;
    }

    public boolean isBot() {
        return iAmBot;
    }

    public void addPieceOnBoard(){
        if(++piecesOnBoard > TOTAL_PIECES) {
            piecesOnBoard--;
            System.out.println("Todas las fichas fueron colocadas en el tablero");
        }
    }

    public int getNumberOfPiecesOnBoard(){
        return piecesOnBoard;
    }

    public Piece[] getListOfPieces() {
        return listOfPieces;
    }

    public Piece getPieceAtIndex(int index){
        return listOfPieces[index];
    }

    public Piece getPieceAtPosition(int x, int y){
        for (Piece piece:
             listOfPieces) {
            if(piece.getPositionX() == x && piece.getPositionY() == y)
                return piece;
        }
        System.out.println("No se encontró la ficha de color " + color + " en la posición (" + x + ", " + y + ").");
        return null;
    }

    public boolean hasPieceSelected(){
        return hasPieceSelected;
    }

    public Piece getSelectedPiece(){
        return listOfPieces[indexPieceSelected];
    }

    public void toggleSelectedPiece(){
        if(indexPieceSelected != -1) {
            listOfPieces[indexPieceSelected].toggleSelected();
            hasPieceSelected = !hasPieceSelected;
        }
    }

    public void setListOfPieces() {
        int i = 0;
        while (i < TOTAL_PIECES) {
            listOfPieces[i] = new Piece(color, 0, 0);
           if(!iAmBot){
               int finalI = i;
               listOfPieces[i].addMouseListener(new MouseAdapter() {
                   @Override
                   public void mouseClicked(MouseEvent e) {
                       //Si es mi turno
                       if (NineMensMorrisGame.getTurn().equals(color)) {
                           //Compruebo si hay ficha seleccionada
                           if (hasPieceSelected) {//Si vuelvo a seleccionar la que ya esta, se deselecciona
                               if (listOfPieces[finalI].isSelected()) {
                                   hasPieceSelected = listOfPieces[finalI].toggleSelected();
                                   indexPieceSelected = -1;
                                   System.out.println("Deseleccione la pieza " + finalI);
                               } else {
                                   System.out.println("No soy la pieza seleccionada, pero mi index es: " + finalI);
                               }
                           } else {//Si no hay, puedo seleccionar una
                               hasPieceSelected = listOfPieces[finalI].toggleSelected();
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
