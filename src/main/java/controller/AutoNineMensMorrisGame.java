package controller;

import model.Player;

import java.util.Objects;
import java.util.Random;

public class AutoNineMensMorrisGame extends NineMensMorrisGame{

    //Clase Random para los movimientos del bot
    private static final Random rng = new Random();
    //Coordenadas aleatorias
    private int randRow, randColumn;

    //Color de las fichas del bot
    private final String color;

    //Constructor por defecto, el bot toma las fichas de color negro
    public AutoNineMensMorrisGame(){
        this("Black");
    }

    //Constructor que recibe como parámetro el color de las fichas del bot
    public AutoNineMensMorrisGame(String color) {
        super(true);
        this.color = color;
        //Se inicializa ambos jugadores
        //Si el bot tiene las fichas blancas, hace el primer movimiento
        if(color.equals("White")){
            player1 = new Player(color, true);
            player1.setListOfPieces();
            player2 = new Player("Black");
            player2.setListOfPieces();
            makeFirstMove();
        }else{
            player1 = new Player("White");
            player1.setListOfPieces();
            player2 = new Player("Black", true);
            player2.setListOfPieces();
        }
    }

    //El bot inicia colocando una ficha
    public void makeFirstMove(){
        //Se obtiene un número aleatorio entre 0 y 6 inclusivos para la fila
        randRow = rng.nextInt(7);
        //Del número obtenido se retorna una lista con los posibles valores que puede tomar la columna
        int[] options = findValidPositionsByIndex(randRow);
        randColumn = Objects.requireNonNull(options)[rng.nextInt(options.length)];

        //Se llama al método setCell para hacer la colocación de la ficha
        super.setCell(randRow, randColumn);
    }

    //Override del método setCell
    //Se hace el movimiento del jugador y de manera seguida el movimiento del bot
    @Override
    public void setCell(int row, int column) {
        if (row >= 0 && row < ROWS && column >= 0 && column < COLUMNS){
            System.out.println("LLamar a superCell");
            super.setCell(row, column);

            if(turn.equals(color) && gameState == GameState.INIT){
                makeAutoSetCell();
            }
        }
    }

    //Movimiento del bot
    public void makeAutoSetCell(){
        do{
            //De un número aleatorio entre 0 y 6 inclusive para la fila
            randRow = rng.nextInt(7);
            //Se retorna una lista de posibles valores para la columna
            int[] options = findValidPositionsByIndex(randRow);
            randColumn = Objects.requireNonNull(options)[rng.nextInt(options.length)];
            System.out.println("Intentar colocar ficha en (" +
                    randColumn + ", " +randRow + ").");
            //Se verifica si casilla con la posición obtenida no tiene una ficha
        }while(getCell(randRow, randColumn).getHasPiece());

        //Y se hace la colocación de dicha ficha
        super.setCell(randRow, randColumn);
    }

    //Método que retorna una lista de posibles valores dependiendo el índice que se le pase como argumento
    private int[] findValidPositionsByIndex(int index){
        switch (index){
            //Si el índice es 0 o 6, los posibles valores para una posición válida son 0, 3 y 6
            case 0:
            case 6:
                return new int[]{0, 3, 6};
            //Si el índice es 1 o 5, los posibles valores para una posición válida son 1, 3 y 5
            case 1:
            case 5:
                return new int[]{1, 3, 5};
            //Si el índice es 2 o 4, los posibles valores para una posición válida son 2, 3 y 4
            case 2:
            case 4:
                return new int[]{2, 3, 4};
            //Si el índice es 3, los posibles valores para una posición válida son 0, 1, 2, 4, 5 y 6
            case 3:
                return new int[]{0, 1, 2, 4, 5, 6};

            //En caso se le pase un indice diferente, el método retorna null
            default:
                System.out.println("Posición invalida");
                return null;
        }
    }
}
