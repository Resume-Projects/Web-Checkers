
package com.webcheckers.model;

import java.util.Arrays;
import java.util.Iterator;

public class BoardView implements Iterable<Row> {

    private Row[] rows;

    public BoardView(Space[][] board) {
        rows = new Row[board.length];
        for(int i = 0; i < rows.length; i++) {
            rows[i] = new Row(i, board[i]);
        }
    }

    @Override
    public Iterator<Row> iterator() {
        return Arrays.stream(rows).iterator();
    }
}
