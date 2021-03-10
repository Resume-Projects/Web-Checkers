package com.webcheckers.model;

/**
 * Represents a Space a Piece can be placed in
 */
public class Space {

    // Classification of the state of the given space

    public enum State {
        INVALID,
        OCCUPIED,
        OPEN
    }

    // Space Attributes

    private int cellIdx;
    private State state;
    private Piece piece;

    /**
     * The constructor for the space used in a given Row
     *
     * @param cellIdx
     *      The column of the space
     * @param piece
     *      THe piece that will sit in that space
     */
    public Space(int cellIdx, Piece piece) {
        this.cellIdx = cellIdx;
        this.piece = piece;
        this.state = State.OCCUPIED;
    }

    public Space(int cellIdx, State state) {
        this.cellIdx = cellIdx;
        this.state = state;
    }

    // Public Methods

    public void setState( State state ) {
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

    public void setPiece (Piece.Type type, Piece.Color color) {
        piece = new Piece(type, color);
    }
}
