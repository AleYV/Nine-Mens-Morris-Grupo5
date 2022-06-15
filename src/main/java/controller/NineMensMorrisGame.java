package controller;

import model.Player;

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
    private static String turn = "White";
    private GameState gameState = GameState.INIT;

    private final Player player1, player2;

    // Crear el cuadrante donde se desarrollara el tablero
    public NineMensMorrisGame(){
        table = new Cells[ROWS][COLUMNS];
        initGame();
        player1 = new Player("White");
        player1.setListOfPieces();
        player2 = new Player("Black");
        player2.setListOfPieces();
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

    public Player getPlayer1(){
        return player1;
    }

    public Player getPlayer2(){
        return player2;
    }

    public Player getCurrentPlayer(){
        return turn.equals("White") ? player1 : player2;
    }

    //Obtener el turno
    public static String getTurn(){
        return turn;
    }

    //Asigna el turno
    public void setTurn(String turn) {
        NineMensMorrisGame.turn = turn;
    }

    public void toggleTurn(){
        if(turn.equals("White")) turn = "Black";
        else turn = "White";
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
            if (turn.equals("White")) table[row][column] = Cells.WHITE;
            else table[row][column] = Cells.BLACK;
            if(gameState == GameState.INIT) toggleTurn();
            return true;
        }
        return false;
    }
    //Comprueba si las dos casillas son vecinas
    public boolean isNeighbour(int prevRow, int prevColum, int row, int column){
        int a=0,b=0;
        if (prevColum==0 || prevColum==6) a =3;
        if (prevColum==1 || prevColum==5) a =2;
        if (prevColum==2 || prevColum==4) a =1;
        if (prevRow==6 || prevRow ==0) b =3;
        if (prevRow==5 || prevRow ==1) b =2;
        if (prevRow==4 || prevRow ==2) b =1;
        if(prevRow == 3){
            if(prevRow+a == row && prevColum==column) return true;
            if(prevRow-a == row && prevColum==column) return true;
            if(prevRow== row && prevColum+1==column) return true;
            return (prevRow==row && prevColum-1==column);
        }
        else if(prevColum == 3){
            if(prevRow == row && b+prevColum == column) return true;
            if(prevRow == row && prevColum - b == column) return true;
            if(prevRow+1 == row && prevColum == column) return true;
            return (prevRow-1 == row && prevColum == column);
        }
        else {
            if(prevRow== row && prevColum+a== column) return true;
            if(prevRow== row && prevColum-a== column) return true;
            if(prevRow+b== row && prevColum == column) return true;
            return (prevRow-b== row && prevColum == column);
        }
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