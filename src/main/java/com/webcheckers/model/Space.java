package com.webcheckers.model;

public class Space {
    private int cellIdx;
    private boolean isValid;
    private Piece piece;

    public Space(int cellIdx, Piece piece) {
        this.cellIdx = cellIdx;
        this.piece = piece;
    }

    public int getCellIdx() {
        return cellIdx;
    }

    public boolean isValid() {
        return isValid;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece (Piece.Type type, Piece.Color color) {
        piece = new Piece(type, color);
    }
}
