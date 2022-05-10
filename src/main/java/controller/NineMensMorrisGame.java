package controller;

public class NineMensMorrisGame {
    protected static final int COLUMNS = 7;
    protected static final int ROWS = 7;

    public enum Cells{
        EMPTY, WHITE, BLACK, DISABLED
    }

    protected Cells[][]table;
    protected char turn;

    public NineMensMorrisGame(){
        table = new Cells[ROWS][COLUMNS];
        initGame();
    }

    private void initGame(){
        for (int i=-3; i<=3; i++){
            for(int j=-3;j<=3;j++){
                if (i==0 && j==0) table[i+3][j+3] = Cells.DISABLED;
                else if(Math.abs(i)==Math.abs(j))
                    table[i+3][j+3] = Cells.EMPTY;
                else if(i==0 && j!=0) table[i+3][j+3] = Cells.EMPTY;
                else if(i!=0 && j==0) table[i+3][j+3] = Cells.EMPTY;
                else table[i+3][j+3] = Cells.DISABLED;
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
