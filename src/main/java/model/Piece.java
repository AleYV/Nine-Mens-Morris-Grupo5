package model;

import javax.swing.ImageIcon;

public class Piece {

    private String color;
    private int positionX, positionY;
    private boolean isSelected;
    private boolean formMill;
    private ImageIcon pieceImage;

    private static final String PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\img\\Piece_";
    private static final String SELECTED = "_Selected.png";
    private static final String MILL = "_Mill.png";

    public Piece(String color, int positionX, int positionY) {
        this.color = color;
        this.positionX = positionX;
        this.positionY = positionY;
        isSelected = false;
        formMill = false;
        pieceImage = new ImageIcon(PATH + color + ".png");
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color){
        this.color = color;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
        if(selected)
            pieceImage = new ImageIcon(PATH + color + SELECTED);
        else
            pieceImage = new ImageIcon(PATH + color + ".png");
    }

    public ImageIcon getPieceImage() {
        return pieceImage;
    }

    public void toggleMill(){
        formMill = !formMill;
        if(formMill)
            pieceImage = new ImageIcon(PATH + color + MILL);
        else
            pieceImage = new ImageIcon(PATH + color + ".png");
    }
}
