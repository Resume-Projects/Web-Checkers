package com.webcheckers.ui;

import com.webcheckers.model.Space;

import java.util.Iterator;

public class Row implements Iterator<Space> {
    private int index;

    public Row(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public Space next() {
        return null;
    }

    @Override
    public void remove() {

    }

    @Override
    public boolean hasNext() {
        return false;
    }

    public Iterator<Space> iterator() {
        return null;
    }
}
