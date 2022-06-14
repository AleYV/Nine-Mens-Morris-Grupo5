package model;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class Cell extends JPanel {
    public static final int CELL_SIZE = 72;
    public static final int HEIGHT_PADDING = 15;

    private int positionX, positionY;

    private JLabel aux;

    public Cell() {
        setEnabled(false);
    }

    public Cell(int i, int j){
        positionX = i;
        positionY = j;
        setBounds(CELL_SIZE*i, (CELL_SIZE*j + HEIGHT_PADDING), CELL_SIZE, CELL_SIZE);
        setBackground(null);
        setOpaque(false);
        add(aux = new JLabel("(" + positionX + ", " + positionY + ")"));
    }

    public void setPiece(Piece piece) {
        remove(aux);
        add(piece);
    }

    public void changeText(String text){
        aux.setText(text);
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
}
