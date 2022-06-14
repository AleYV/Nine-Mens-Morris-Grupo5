package model;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Piece extends JLabel{

    private String color;
    private int positionX, positionY;
    private boolean isSelected;
    private boolean formMill;

    private static final String PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\img\\Piece_";
    private static final String SELECTED = "_Selected.png";
    private static final String MILL = "_Mill.png";

    public Piece(String color, int positionX, int positionY) {
        this.color = color;
        this.positionX = positionX;
        this.positionY = positionY;
        isSelected = false;
        formMill = false;

        setIcon(new ImageIcon(PATH + color + ".png"));
        setDisabledIcon(new ImageIcon(PATH + "Null.png"));
        setBackground(null);
        setHorizontalAlignment(JLabel.CENTER);
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

    public boolean toggleSelected() {
        isSelected = !isSelected;
        if(isSelected) {
            setIcon(new ImageIcon(PATH + color + SELECTED));
            System.out.println("Cambie de icono a seleccionado");
        }
        else {
            setIcon(new ImageIcon(PATH + color + ".png"));
            System.out.println("Cambie de icono a des seleccionado");
        }
        
        return isSelected;
    }

    public void toggleEnabled(){
        setEnabled(!this.isEnabled());
    }

    public void toggleMill() {
        formMill = !formMill;
        if (formMill)
            setIcon(new ImageIcon(PATH + color + MILL));
        else
            setIcon(new ImageIcon(PATH + color + ".png"));
    }

}
