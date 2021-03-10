package com.webcheckers.model;

/**
 * The Space data type
 */

public class Space {

    public enum State {
        INVALID,
        OCCUPIED,
        OPEN
    }

    private int cellIdx;
    private State state;
    private Piece piece;

    /**
     * The space data type if there is a piece on it
     *
     * @param cellIdx the cellIdx
     * @param piece the game piece on the board
     */
    public Space(int cellIdx, Piece piece) {
        this.cellIdx = cellIdx;
        this.piece = piece;
        this.state = State.OCCUPIED;
    }

    /**
     * The space data type
     *
     * @param cellIdx the cellIdx
     * @param state the state of that space
     */
    public Space(int cellIdx, State state) {
        this.cellIdx = cellIdx;
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
