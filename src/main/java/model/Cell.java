package model;

import javax.swing.JPanel;

public class Cell extends JPanel {
    public static final int CELL_SIZE = 72;
    public static final int HEIGHT_PADDING = 15;

    private final int positionX, positionY;

   public Cell(int i, int j){
        positionX = i;
        positionY = j;
        setBounds(CELL_SIZE*i, (CELL_SIZE*j + HEIGHT_PADDING), CELL_SIZE, CELL_SIZE);
        setBackground(null);
        setOpaque(false);
    }

    public void setPiece(Piece piece) {
        removeAll();
        piece.setPositionX(positionX);
        piece.setPositionY(positionY);
        add(piece);
        revalidate();
        repaint();
    }

    public void removePiece(){
        removeAll();
        revalidate();
        repaint();
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
}
