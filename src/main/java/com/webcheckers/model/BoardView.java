package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Provides an iterable view of the checkers board
 *
 * @author Danny Gardner
 */
public class BoardView implements Iterable<Row>{
    private List<Row> rows;

    public BoardView(Space[][] board) {
        rows = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            rows.add(new Row(i, board[i]));
        }
    }

    public Iterator<Row> iterator() {
        return rows.iterator();
    }
}
