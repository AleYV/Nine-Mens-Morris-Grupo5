package controller;

import model.Cell;
import model.Player;

public class NineMensMorrisGame {
    protected static final int COLUMNS = 7;
    protected static final int ROWS = 7;

    public enum Cells{
        EMPTY, WHITE, BLACK, DISABLED
    }

    public enum GameState{
        INIT, PLAYING, WIN, LOSE, DRAW
    }

    protected Cells[][] table;
    protected Cell[][] tableG = new Cell[ROWS][COLUMNS];
    protected static String turn = "White";
    protected GameState gameState = GameState.INIT;

    protected Player player1, player2;

    // Crear el cuadrante donde se desarrollara el tablero
    public NineMensMorrisGame(boolean iAmBot){
        table = new Cells[ROWS][COLUMNS];
        initGame();
        customInitGame();
        if(!iAmBot) initPlayers();
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

    private void customInitGame(){
        for (int i = -3; i <= 3; i++){
            for(int j = -3; j <= 3; j++){
                if (i == 0 && j == 0) continue;

                if(i == 0) {
                    tableG[3][j + 3] = new Cell(3, j + 3);
                    continue;
                }
                if(j == 0) {
                    tableG[i + 3][3] = new Cell(i + 3, 3);
                    continue;
                }
                if(Math.abs(i) == Math.abs(j)) {
                    tableG[i + 3][j + 3] = new Cell(i + 3, j + 3);
                }
            }
        }
    }

    public Cell[][] getTableG(){
        return tableG;
    }

    private void initPlayers(){
        player1 = new Player("White");
        player1.setListOfPieces();
        player2 = new Player("Black");
        player2.setListOfPieces();
    }

    public Player getCurrentPlayer(){
        return turn.equals("White") ? player1 : player2;
    }

    public Player getRivalPlayer(){
        return turn.equals("Black") ? player1 : player2;
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

    public void customSetCell(int row, int column){
        if (row >= 0 && row < ROWS && column >= 0 && column < COLUMNS){
            tableG[column][row].setPiece(getCurrentPlayer().getPieceAtIndex(getCurrentPlayer().getNumberOfPiecesOnBoard()));
            getCurrentPlayer().addPieceOnBoard();
            //if(gameState == GameState.INIT) toggleTurn();
            setCell(row, column);
        }
    }

    //Comprueba si las dos casillas son vecinas
    public boolean isNeighbour(int prevRow, int prevColumn, int row, int column){
        int a=0,b=0;
        if (prevColumn==0 || prevColumn==6) a =3;
        if (prevColumn==1 || prevColumn==5) a =2;
        if (prevColumn==2 || prevColumn==4) a =1;
        if (prevRow==6 || prevRow ==0) b =3;
        if (prevRow==5 || prevRow ==1) b =2;
        if (prevRow==4 || prevRow ==2) b =1;
        if(prevRow == 3){
            if(prevRow+a == row && prevColumn==column) return true;
            if(prevRow-a == row && prevColumn==column) return true;
            if(prevRow== row && prevColumn+1==column) return true;
            return (prevRow==row && prevColumn-1==column);
        }
        else if(prevColumn == 3){
            if(prevRow == row && b+prevColumn == column) return true;
            if(prevRow == row && prevColumn - b == column) return true;
            if(prevRow+1 == row && prevColumn == column) return true;
            return (prevRow-1 == row && prevColumn == column);
        }
        else {
            if(prevRow== row && prevColumn+a== column) return true;
            if(prevRow== row && prevColumn-a== column) return true;
            if(prevRow+b== row && prevColumn == column) return true;
            return (prevRow-b== row && prevColumn == column);
        }
    }
    //Comprueba si el movimiento es valido y retorna un booleano
    //Si retorna true, significa que actualizo la informacion de table (el tablero)
    public boolean makeMove(int prevRow, int prevColumn, int row, int column){
            if (row >= 0 && row < ROWS && column >= 0 && column < COLUMNS){
                if(setCell(row, column)) {
                    table[prevRow][prevColumn] = Cells.EMPTY;
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