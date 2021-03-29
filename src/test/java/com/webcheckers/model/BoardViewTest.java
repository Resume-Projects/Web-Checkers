package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-Tier")
public class BoardViewTest {
    private BoardView CuT;
    private Row[] rows;

    @BeforeEach
    public void setUp() {
        rows = new Row[8];
        CuT = new BoardView(rows);
    }

    @Test
    public void iterator_test() {
        assertNotNull(CuT.iterator());
    }
}
