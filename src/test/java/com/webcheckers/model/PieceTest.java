package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.webcheckers.model.Piece.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Coverage for the Piece class
 */
@Tag("Model-Tier")
public class PieceTest {

    private Piece CuT;
    private Color color;
    private Type type;

    /**
     * Sets up a simple red piece to perform tests on
     */
    @BeforeEach
    public void setup() {
        color = Color.RED;
        type = Type.SINGLE;
        CuT = new Piece(type, color);
    }

    /**
     * Performs simple tests on a simple red piece
     */
    @Test
    public void testPiece() {
        assertNotNull(CuT.getColor());
        assertNotNull(CuT.getType());
    }
}
