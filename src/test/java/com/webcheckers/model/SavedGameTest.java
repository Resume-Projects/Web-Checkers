package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-Tier")
public class SavedGameTest {

    private Row CuT;
    private int index;
    private Space[] spaces;

    @BeforeEach
    public void setUp() {
        index = 3;
        spaces = new Space[7];
        CuT = new Row(index, spaces);
    }

    @Test
    public void getIndex() {
        assertSame(CuT.getIndex(), 3);
    }

    @Test
    public void getSpace() {
        Space space = new Space(0, Space.State.OPEN);
        spaces[0] = space;
        //CuT = new Row(index, spaces);
        assertSame(CuT.getSpace(0), space);
    }

    @Test
    public void iterator() {
        assertNotNull(CuT.iterator());
    }
}
