package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * The Row data type.
 */
public class Row implements Iterable<Space> {
    private int index;
    private List<Space> spaces;

    /**
     * The Row data type.
     *
     * @param index the row index
     */
    public Row(int index, Space[] rowSpaces) {
        this.index = index;
        spaces = new ArrayList<>();
        Collections.addAll(spaces, rowSpaces);
    }

    /**
     * Get the index of this row within the board.
     *
     * @return the index of this row (0-7)
     */
    public int getIndex() {
        return index;
    }

    public Space getSpace(int col) {
        return spaces.get(col);
    }

    /**
     * Create a Java Iterator of the Spaces within a single row.
     *
     * @return null
     */
    @Override
    public Iterator<Space> iterator() {
        return spaces.iterator();
    }
}
