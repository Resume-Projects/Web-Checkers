package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-Tier")
public class PositionTest {
    private Position CuT;
    private int row;
    private int cell;

    /**
     * Sets up a position to perform tests on
     */
    @BeforeEach
    public void setup() {
        row = 0;
        cell = 5;
        CuT = new Position(row, cell);
    }

    @Test
    public void getRow_test() {
        assertSame(CuT.getRow(), 0);
    }

    @Test
    public void getCell_test() {
        assertSame(CuT.getCell(), 5);
    }
}
