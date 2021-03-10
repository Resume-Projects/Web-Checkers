package com.webcheckers.model;

/**
 * The Piece data type
 */
public class Piece {

    public enum Type {SINGLE, KING}
    public enum Color {RED, WHITE}

    private Type type;
    private Color color;

    /**
     * The piece data type.
     *
     * @param type the piece type
     * @param color the piece color
     */
    public Piece(Type type, Color color) {
        this.type = type;
        this.color = color;
    }

    /**
     * Gets the pieces type
     *
     * @return the piece type
     */
    public Type getType() {
        return type;
    }

    /**
     * Gets the pieces color
     *
     * @return the piece color
     */
    public Color getColor() {
        return color;
    }
}
