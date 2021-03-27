
package com.webcheckers.model;

import java.util.Arrays;
import java.util.Iterator;

/**
 * The BoardView data type that allows the board to be rendered
 */
public class BoardView implements Iterable<Row> {

    private final Row[] rows;

    /**
     * Creates the board view by making an array of Rows from the board
     * @param rows the entire board in the form of an array of rows
     */
    public BoardView(Row[] rows) {
        this.rows = rows;
    }

    /**
     * Loops over all the rows in a board
     *
     * @return a stream of all the rows in the board
     */
    @Override
    public Iterator<Row> iterator() {
        return Arrays.stream(rows).iterator();
    }
}
