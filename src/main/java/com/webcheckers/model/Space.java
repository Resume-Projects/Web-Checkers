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
     * @param cellIdx The column of the space
     * @param piece The piece that will sit in that space
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
    /**
     * Finds the cellIdx
     *
     * @return the cellIdx
     */
    public int getCellIdx() {
        return cellIdx;
    }

    /**
     * Determines if the space is unoccupied
     *
     * @return true if the spaces state is OPEN, return false otherwise
     */
    public boolean isValid() {
        return this.state == State.OPEN;
    }

    /**
     * Get the piece on that space
     *
     * @return the piece on that space
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Sets a piece on that space
     *
     * @param type the type of piece on that space
     * @param color the color of the piece on that space
     */
    public void setPiece (Piece.Type type, Piece.Color color) {
        piece = new Piece(type, color);
    }
}
