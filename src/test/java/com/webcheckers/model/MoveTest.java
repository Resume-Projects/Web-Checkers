package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-Tier")
public class MoveTest {
    private Move CuT;
    private Position start;
    private Position end;

    @BeforeEach
    public void setUp() {
        start = new Position(1, 4);
        end = new Position(2, 5);
        CuT = new Move(start, end);
    }

    @Test
    public void getStart_test() {
        assertSame(CuT.getStart(), start);
    }

    @Test
    public void getEnd_test() {
        assertSame(CuT.getEnd(), end);
    }
}
