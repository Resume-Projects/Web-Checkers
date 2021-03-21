package com.webcheckers.model;

public class Position {

    private final int row;
    private final int cell;

    public Position(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    public int getRow() {
        return row;
    }

    public int getCell() {
        return cell;
    }

}
