package com.webcheckers.model;

public class Space {

    public enum State {
        INVALID,
        OCCUPIED,
        OPEN
    }

    private int cellIdx;
    private State state;
    private Piece piece;

    public Space(int cellIdx, Piece piece) {
        this.cellIdx = cellIdx;
        this.piece = piece;
        this.state = State.OCCUPIED;
    }

    public Space(int cellIdx, State state) {
        this.cellIdx = cellIdx;
        this.state = state;
    }

    public int getCellIdx() {
        return cellIdx;
    }

    public boolean isValid() {
        return this.state == State.OPEN;
    }

    public Piece getPiece() {
        return piece;
    }
}
