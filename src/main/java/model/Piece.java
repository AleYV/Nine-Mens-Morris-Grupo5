package model;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Piece extends JLabel{

    //Color de la ficha
    private String color;
    //Posición de la ficha
    private int positionX, positionY;
    //¿Estoy seleccionada?
    private boolean isSelected;
    //¿Formo parte de un molino?
    private boolean formMill;

    //Strings que se usan para el cambio de imagen
    private static final String PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\img\\Piece_";
    private static final String SELECTED = "_Selected.png";
    private static final String MILL = "_Mill.png";

    //Constructor que recibe el color y la posición de la ficha
    public Piece(String color, int positionX, int positionY) {
        this.color = color;
        this.positionX = positionX;
        this.positionY = positionY;
        //Por defecto no está seleccionado, ni forma un molino
        isSelected = false;
        formMill = false;

        //Se le establece un icono con el color que se le fue pasado como argumento
        setIcon(new ImageIcon(PATH + color + ".png"));
        setDisabledIcon(new ImageIcon(PATH + "Null.png"));
        setBackground(null);
        setHorizontalAlignment(JLabel.CENTER);
    }

    //Retorna el color de la ficha
    public String getColor() {
        return color;
    }

    //Cambia el color de la ficha
    public void setColor(String color){
        this.color = color;
    }

    //Obtiene la coordenada X de la ficha respecto al tablero
    public int getPositionX() {
        return positionX;
    }

    //Asigna la coordenada X de la ficha respecto al tablero
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    //Obtiene la coordenada Y de la ficha respecto al tablero
    public int getPositionY() {
        return positionY;
    }

    //Asigna la coordenada Y de la ficha respecto al tablero
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    //Retorna si la ficha está seleccionada o no
    public boolean isSelected() {
        return isSelected;
    }

    //Palanca: Si la ficha está seleccionada, pasa a deseleccionada y se le cambia su icono,
    //caso similar si la ficha ya está deseleccionada
    public void toggleSelected() {
        isSelected = !isSelected;
        if(isSelected) {
            setIcon(new ImageIcon(PATH + color + SELECTED));
            System.out.println("Cambie de icono a seleccionado");
        }
        else {
            setIcon(new ImageIcon(PATH + color + ".png"));
            System.out.println("Cambie de icono a des seleccionado");
        }
    }

    //Palanca: Si la ficha está habilitada, la deshabilita
    //caso similar si la ficha ya está deshabilitada
    public void toggleEnabled(){
        setEnabled(!this.isEnabled());
    }

    //Palanca: Si la ficha no pertenecía a un molino, para a formar parte de un molino y se le cambia el icono
    //caso similar si la ficha ya formaba parte de un molino
    public void toggleMill() {
        formMill = !formMill;
        if (formMill)
            setIcon(new ImageIcon(PATH + color + MILL));
        else
            setIcon(new ImageIcon(PATH + color + ".png"));
    }

}
