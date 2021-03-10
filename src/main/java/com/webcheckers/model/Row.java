package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The Row data type.
 */
public class Row implements Iterable<Space> {

    private int index;
    private Space[] spaces;

    /**
     * The Row data type.
     *
     * @param index the row index
     */
    public Row(int index, Space[] spaces) {
        this.index = index;
        this.spaces = spaces;
    }

    /**
     * Get the index of this row within the board.
     *
     * @return the index of this row (0-7)
     */
    public int getIndex() {
        return index;
    }

    /**
     * Get the index of this col in the board
     *
     * @param col the column of the board
     * @return the column of the board
     */
    public Space getSpace(int col) {
        return spaces[col];
    }

    /**
     * Create a Java Iterator of the Spaces within a single row.
     *
     * @return null
     */
    @Override
    public Iterator<Space> iterator() {
        return Arrays.stream(spaces).iterator();
    }
}
