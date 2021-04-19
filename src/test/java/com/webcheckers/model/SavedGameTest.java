package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-Tier")
public class SavedGameTest {

    private SavedGame CuT;

    @BeforeEach
    public void setUp() {
        ArrayList<SavedMove> savedMoves = new ArrayList<>();
        savedMoves.add(new SavedMove(new Space[8][8], Piece.Color.RED));
        Player redPlayer = new Player("Kev");
        Player whitePlayer = new Player("in");
        int gameID = 3;
        CuT = new SavedGame(savedMoves, redPlayer, whitePlayer, gameID);
    }

    @Test
    public void getTurnNumTest() {
        assertEquals(CuT.getTurnNum(), 0);
    }

    @Test
    public void setTurnNumTest() {
        CuT.setTurnNum(4);
    }

    @Test
    public void getPlayerWatchingTest() {
        assertNull(CuT.getPlayerWatching());
    }

    @Test
    public void setPlayerTest() {
        CuT.setPlayerWatching(new Player("REEEEEEE"));
    }

    @Test
    public void hasNextTest() {
        assertFalse(CuT.hasNext());
    }

    @Test
    public void hasPreviousTest() {
        assertFalse(CuT.hasPrevious());
    }

    @Test
    public void getSavedMovesTest() {
        assertNotNull(CuT.getSavedMoves());
    }

    @Test
    public void getGameTest() {
        assertNotNull(CuT.getGame());
    }

    @Test
    public void updateBoardTest() {
        CuT.updateBoard();
    }

}
