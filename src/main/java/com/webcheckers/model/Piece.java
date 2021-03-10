package com.webcheckers.model;

/**
 * Data Type for Pieces that play in the Checkers Game
 */
public class Piece {


    // Classification for the Piece's type

    public enum Type {SINGLE, KING}

    // Classification for the Piece's color

    public enum Color {RED, WHITE}

    //
    // Piece Attributes
    //

    private Type type;
    private Color color;

    /**
     * The constructor for a game Piece
     *
     * @param type
     *      The type of the piece
     * @param color
     *      The color of the given piece
     */
    public Piece(Type type, Color color) {
        this.type = type;
        this.color = color;
    }

    //
    // Public Methods
    //

    public Type getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }
}
