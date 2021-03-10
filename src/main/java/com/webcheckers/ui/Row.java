package com.webcheckers.ui;

import com.webcheckers.model.Space;

import java.util.Iterator;
/**
 * The Row data type.
 */
public class Row implements Iterable<Space> {
    private int index;

    /**
     * The Row data type.
     * @param index the row index
     */
    public Row(int index) {
        this.index = index;
    }

    /**
     * Get the index of this row within the board.
     * @return the index of this row (0-7)
     */
    public int getIndex() {
        return index;
    }

    /**
     * Create a Java Iterator of the Spaces within a single row.
     * @return null
     */
    @Override
    public Iterator<Space> iterator() {
        return null;
    }
}
