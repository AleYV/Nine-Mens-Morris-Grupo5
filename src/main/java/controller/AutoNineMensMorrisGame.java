package controller;

import model.Player;

import java.util.Objects;
import java.util.Random;

public class AutoNineMensMorrisGame extends NineMensMorrisGame{

    private static final Random rng = new Random();
    private int randRow, randColumn;

    private final String color;

    public AutoNineMensMorrisGame(){
        this("Black");
    }

    public AutoNineMensMorrisGame(String color) {
        super(true);
        this.color = color;
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

    public void makeFirstMove(){
        randRow = rng.nextInt(7);
        int[] options = findValidPositionsByIndex(randRow);
        randColumn = Objects.requireNonNull(options)[rng.nextInt(options.length)];

        super.customSetCell(randRow, randColumn);
    }

    @Override
    public void customSetCell(int row, int column) {
        if (row >= 0 && row < ROWS && column >= 0 && column < COLUMNS){
            System.out.println("LLamar a superCell");
            super.customSetCell(row, column);

            if(turn.equals(color) && gameState == GameState.INIT){
                makeAutoSetCell();
            }
        }
    }

    public void makeAutoSetCell(){
        do{
            randRow = rng.nextInt(7);
            int[] options = findValidPositionsByIndex(randRow);
            randColumn = Objects.requireNonNull(options)[rng.nextInt(options.length)];
            System.out.println("Intentar colocar ficha en (" +
                    randColumn + ", " +randRow + ").");
        }while(!(getCell(randRow, randColumn) == Cells.EMPTY));

        super.customSetCell(randRow, randColumn);
    }

    private int[] findValidPositionsByIndex(int index){
        switch (index){
            case 0:
            case 6:
                return new int[]{0, 3, 6};
            case 1:
            case 5:
                return new int[]{1, 3, 5};
            case 2:
            case 4:
                return new int[]{2, 3, 4};
            case 3:
                return new int[]{0, 1, 2, 4, 5, 6};

            default:
                System.out.println("Posición invalida");
                return null;
        }
    }
}
