package com.webcheckers.model;

/**
 * The Move data type
 */
public class Move {

    private final Position start;
    private final Position end;

    /**
     * The Move data type
     *
     * @param start The start position
     * @param end The end position
     */
    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    /**
     * The start position
     *
     * @return The end position
     */
    public Position getStart() {
        return start;
    }

    /**
     * The end position
     *
     * @return The end position
     */
    public Position getEnd() {
        return end;
    }
}
