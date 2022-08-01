package controller;

import model.Cell;
import model.Piece;
import model.Player;

public class NineMensMorrisGame {
    //Dimensión del tablero
    protected static final int COLUMNS = 7;
    protected static final int ROWS = 7;

    //Fase de estado del juego
    public enum GameState{
        INIT, PLAYING, WIN, LOSE, DRAW
    }

    //Matriz de Celdas que representan a las casillas del tablero
    protected Cell[][] table = new Cell[ROWS][COLUMNS];

    //Turno del juego (inicia con las fichas blancas)
    protected static String turn = "White";

    //Estado actual del juego (inicia por INIT)
    protected static GameState gameState = GameState.INIT;

    //Jugadores
    protected Player player1, player2;

    //Constructor que recibe como parámetro un boolean para saber si el rival es un bot o no
    public NineMensMorrisGame(boolean iAmBot){
        initGame();
        if(!iAmBot) initPlayers();
    }

    //Que posiciones de la matriz son válidas para asignarle casillas del juego al tablero
    private void initGame(){
        for (int i = -3; i <= 3; i++){
            for(int j = -3; j <= 3; j++){
                if (i == 0 && j == 0) continue;

                if(i == 0) {
                    table[3][j + 3] = new Cell(3, j + 3);
                    continue;
                }
                if(j == 0) {
                    table[i + 3][3] = new Cell(i + 3, 3);
                    continue;
                }
                if(Math.abs(i) == Math.abs(j)) {
                    table[i + 3][j + 3] = new Cell(i + 3, j + 3);
                }
            }
        }
    }

    //Retorna la matriz de celdas
    public Cell[][] getTable(){
        return table;
    }


    //Inicializa a los jugadores
    private void initPlayers(){
        player1 = new Player("White");
        player1.setListOfPieces();
        player2 = new Player("Black");
        player2.setListOfPieces();
    }

    //Retorna al jugador del turno actual
    public Player getCurrentPlayer(){
        return turn.equals("White") ? player1 : player2;
    }

    //Retorna al rival del jugador del turno actual
    public Player getRivalPlayer(){
        return turn.equals("Black") ? player1 : player2;
    }

    //Retorna el turno
    public static String getTurn(){
        return turn;
    }

    //Asigna el turno
    public void setTurn(String turn) {
        NineMensMorrisGame.turn = turn;
    }

    //Palanca: Cambia el turno por Black, si anteriormente el turno era White
    //de manera similar si el turno era Black
    public void toggleTurn(){
        if(turn.equals("White")) turn = "Black";
        else turn = "White";
    }

    //Retorna el número de columnas
    public int getColumns(){
        return COLUMNS;
    }

    //Retorna el número de filas
    public int getRows(){
        return ROWS;
    }

    //Retorna el estado actual del juego
    public static GameState getGameState() {
        return gameState;
    }

    //Asigna el estado actual del juego
    public void setGameState(GameState gameState) {
        NineMensMorrisGame.gameState = gameState;
    }


    //Retorna la celda verificando que esté en el rango
    public Cell getCell(int row, int column){
        //Si no está dentro del rango, retorna null
        if(row >= 0 && row < ROWS && column >= 0 && column < COLUMNS){
            return table[row][column];
        }
        return null;
    }

    //Coloca la ficha correspondiente al turno y cambia el turno
    public void setCell(int row, int column){
        if (row >= 0 && row < ROWS && column >= 0 && column < COLUMNS){
            table[column][row].setPiece(getCurrentPlayer().getPieceAtIndex(getCurrentPlayer().getNumberOfPiecesOnBoard()));
            getCurrentPlayer().addPieceOnBoard();
            if(gameState == GameState.INIT) toggleTurn();
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
    //Comprueba si el movimiento es válido y retorna un booleano
    //Si retorna true, significa que actualizó la información del tablero
    public boolean makeMove(int prevRow, int prevColumn, int row, int column){
            if (row >= 0 && row < ROWS && column >= 0 && column < COLUMNS){
                table[prevColumn][prevRow].removePiece();
                return true;
            }
        return false;
    }

    //Verifica si el movimiento realizado ha formado un molino
    public boolean moveMakeMill(Player player,Cell[][] casillas,int newRow, int newColumn){
        System.out.println("-----------");
        int[] arreglo = casillas[newColumn][newRow].getNeighbours();
        if(casillas[arreglo[0]][newRow].getHasPiece() == true && casillas[arreglo[1]][newRow].getHasPiece() == true){
            if(casillas[arreglo[0]][newRow].getColorPiece() == player.getColor() && casillas[arreglo[1]][newRow].getColorPiece() == player.getColor()){
                player.getPieceAtPosition(arreglo[0],newRow).pieceFormMill();
                player.getPieceAtPosition(arreglo[1],newRow).pieceFormMill();
                player.getPieceAtPosition(newColumn,newRow).pieceFormMill();
                System.out.println("Hay molino");
                System.out.println("-----------");
                return true;
            }
        } else if (casillas[newColumn][arreglo[2]].getHasPiece() == true && casillas[newColumn][arreglo[3]].getHasPiece() == true) {
            if(casillas[newColumn][arreglo[2]].getColorPiece() == player.getColor() && casillas[newColumn][arreglo[3]].getColorPiece() == player.getColor()){
                player.getPieceAtPosition(newColumn,arreglo[2]).pieceFormMill();
                player.getPieceAtPosition(newColumn,arreglo[3]).pieceFormMill();
                player.getPieceAtPosition(newColumn,newRow).pieceFormMill();
                System.out.println("Hay molino");
                System.out.println("-----------");
                return true;
            }
        } else{
            System.out.println("No hay molino");
            System.out.println("-----------");
        }
        return false;
    }

}