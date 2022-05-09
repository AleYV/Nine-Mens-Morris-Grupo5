package controller;

public class NineMensMorrisGame {
    protected static final int COLUMNS = 7;
    protected static final int ROWS = 7;

    public enum Celdas{
        EMPTY, WHITE, BLACK, DISABLED
    }

    protected Celdas[][]tablero;
    protected char turn;

    public NineMensMorrisGame(){
        tablero = new Celdas[ROWS][COLUMNS];
        initGame();
    }

    private void initGame(){
        for (int i=-3; i<=3; i++){
            for(int j=-3;j<3;j++){
                if(i!=0 &&  j!=0)
                    if(Math.abs(i)==Math.abs(j))
                        tablero[i+3][j+3] = Celdas.EMPTY;
                if(i==0 && j!=0) tablero[i+3][j+3] = Celdas.EMPTY;
                if(i!=0 && j==0) tablero[i+3][j+3] = Celdas.EMPTY;
                else tablero[i+3][j+3] = Celdas.DISABLED;
            }
        }
    }

    public char getTurn(){
        return turn;
    }

    public int getColumns(){
        return COLUMNS;
    }
    public int getRows(){
        return ROWS;
    }
}
