package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
public class SpaceTest {
    private Space CuT;
    private Piece piece;
    private int cellIdx;

    @Test
    public void ctor_Piece() {
        // Setup a piece to test constructor
        piece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        cellIdx = 0;
        CuT = new Space(cellIdx, piece);

        assertNotNull(CuT);
    }

    @Test
    public void ctor_StateValidId() {
        CuT = new Space(cellIdx, Space.State.OPEN);
        assertNotNull(CuT);
    }

    @Test
    public void validSpace_test() {
        CuT = new Space(cellIdx, Space.State.OPEN);
        assertTrue(CuT.isValid());
    }

    @Test
    public void InvalidSpace_test() {
        CuT = new Space(cellIdx, Space.State.INVALID);
        assertFalse(CuT.isValid());
    }

    @Test
    public void getPiece_test() {
        piece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
        cellIdx = 0;
        CuT = new Space(cellIdx, piece);

        assertNotNull(CuT.getPiece());
    }

    @Test
    public void getCellIdx_test() {
        cellIdx = 7;
        CuT = new Space(cellIdx, Space.State.OPEN);

        assertSame(CuT.getCellIdx(), 7);
    }

    @Test
    public void setPiece_test() {
        cellIdx = 0;
        CuT = new Space(cellIdx, Space.State.OPEN);
        piece = new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);
        CuT.setPiece(piece);

        assertSame(CuT.getPiece(), piece);
    }

    @Test
    public void getPieceColor_test() {
        CuT = new Space(0, new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
        assertSame(CuT.getPieceColor(), Piece.Color.WHITE);
    }

    @Test
    public void getPieceType_test() {
        CuT = new Space(0, new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
        assertSame(CuT.getPieceType(), Piece.Type.SINGLE);
    }

    @Test
    public void getState_test() {
        CuT = new Space(0, Space.State.OPEN);
        assertSame(CuT.getState(), Space.State.OPEN);
    }
}
