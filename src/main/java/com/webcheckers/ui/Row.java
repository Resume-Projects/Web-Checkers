package com.webcheckers.ui;

import com.webcheckers.model.Space;

import java.util.Iterator;

public class Row implements Iterable<Space> {
    private int index;

    public Row(int index) {
        this.index = index;
    }

    @Override
    public Iterator<Space> iterator() {
        return null;
    }
}
