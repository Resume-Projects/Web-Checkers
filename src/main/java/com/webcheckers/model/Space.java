package com.webcheckers.model;

public class Space {
    private int cellIdx;
    private boolean isValid;
    private Piece piece;

    public Space(int cellIdx) {
        this.cellIdx = cellIdx;
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
}
