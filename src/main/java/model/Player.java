package model;

public class Player {

    private int totalPieces;
    private Piece[] listPiece;

    public Player() {
    }

    public Player(int totalPieces, Piece[] listPiece) {
        this.totalPieces = totalPieces;
        this.listPiece = listPiece;
    }

    public int getTotalPieces() {
        return totalPieces;
    }

    public void setTotalPieces(int totalPieces) {
        this.totalPieces = totalPieces;
    }

    public Piece[] getListPiece() {
        return listPiece;
    }

    public void setListPiece(Piece[] listPiece) {
        this.listPiece = listPiece;
    }
}
