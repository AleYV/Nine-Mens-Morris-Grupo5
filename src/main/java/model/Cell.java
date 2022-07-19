package model;

import javax.swing.JPanel;

public class Cell extends JPanel {

    //Dimensiones de la celda
    public static final int CELL_SIZE = 72;
    public static final int HEIGHT_PADDING = 15;

    //Posición de la celda respecto al tablero
    private final int positionX, positionY;

    //¿La celda tiene una pieza?
    private boolean hasPiece = false;

    //Constructor que recibe los parámetros de su posición
    public Cell(int i, int j){
        positionX = i;
        positionY = j;
        setBounds(CELL_SIZE*i, (CELL_SIZE*j + HEIGHT_PADDING), CELL_SIZE, CELL_SIZE);
        setBackground(null);
        setOpaque(false);
    }

    //Se le asigna una pieza a la celda
    public void setPiece(Piece piece) {
        removeAll();
        piece.setPositionX(positionX);
        piece.setPositionY(positionY);
        add(piece);
        hasPiece = true;
        revalidate();
        repaint();
    }

    //Se remueve la pieza que tuviese
    public void removePiece(){
       hasPiece = false;
       removeAll();
       revalidate();
       repaint();
    }

    //Retorna la coordenada X de la celda
    public int getPositionX() {
        return positionX;
    }

    //Retorna la coordenada Y de la celda
    public int getPositionY() {
        return positionY;
    }

    //Retorna si la celda tiene una pieza o no
    public boolean getHasPiece(){
       return hasPiece;
    }
}
