package com.webcheckers.model;

/**
 * The Position data type
 */
public class Position {

    private final int row;
    private final int cell;

    /**
     * The Position data type
     *
     * @param row the row
     * @param cell the col
     */
    public Position(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    /**
     * The Row to get
     *
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * The Col to get
     *
     * @return the col
     */
    public int getCell() {
        return cell;
    }

}
