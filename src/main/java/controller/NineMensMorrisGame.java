package controller;

public class NineMensMorrisGame {
    private static final int COLUMNS = 7;
    private static final int ROWS = 7;

    public enum Cells{
        EMPTY, WHITE, BLACK, DISABLED
    }

    public enum GameState{
        INIT, PLAYING, WIN, LOSE, DRAW
    }

    private Cells[][] table;
    private boolean turn = true;
    private GameState gameState = GameState.INIT;

    // Crear el cuadrante donde se desarrollara el tablero
    public NineMensMorrisGame(){
        table = new Cells[ROWS][COLUMNS];
        initGame();
    }

    //Que posiciones de la matriz son válidas para asignarle casillas del juego al tablero
    private void initGame(){
        for (int i=-3; i<=3; i++){
            for(int j=-3;j<=3;j++){
                if (i==0 && j==0) table[i+3][j+3] = Cells.DISABLED;
                else if(Math.abs(i)==Math.abs(j))
                    table[i+3][j+3] = Cells.EMPTY;
                else if(i==0) table[i+3][j+3] = Cells.EMPTY;
                else if(j==0) table[i+3][j+3] = Cells.EMPTY;
                else table[i+3][j+3] = Cells.DISABLED;
            }
        }
    }

    //Obtener el turno
    public boolean getTurn(){
        return turn;
    }

    //Asigna el turno
    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    //Obtener columnas
    public int getColumns(){
        return COLUMNS;
    }

    //Obtener filas
    public int getRows(){
        return ROWS;
    }

    //Obtener el estado del juego
    public GameState getGameState() {
        return gameState;
    }

    //Asigna el estado del juego
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Cells[][] getTable() {
        return table;
    }


    //Obtener la celda verificando que este en el rango
    public Cells getCell(int row, int column){
        if (row >= 0 && row < ROWS && column >= 0 && column < COLUMNS){
            return table[row][column];
        }
        return null;
    }

    //Coloca la ficha correspondiente al turno y cambia el turno
    public boolean setCell(int row, int column){
        if (row >= 0 && row < ROWS && column >= 0 && column < COLUMNS){
            if (turn) table[row][column] = Cells.WHITE;
            else table[row][column] = Cells.BLACK;
            if(gameState == GameState.INIT)
                turn = !turn;
            return true;
        }
        return false;
    }

    //Comprueba si el movimiento es valido y retorna un booleano
    //Si retorna true, significa que actualizo la informacion de table (el tablero)
    public boolean makeMove(int prevRow, int prevColum, int row, int column){
        if (row >= 0 && row < ROWS && column >= 0 && column < COLUMNS){
            if(setCell(row, column)) {
                table[prevRow][prevColum] = Cells.EMPTY;
                return true;
            }
        }
        return false;
    }

    //Verifica si el movimiento hecho forma un molino
    public String moveMakeMill(){
        String mill = "";
        //Hacer logica
        return mill;
    }
}