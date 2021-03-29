package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.webcheckers.model.CheckersGame.*;
import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-Tier")
public class CheckersGameTest {
    private CheckersGame CuT;
    private Player redPlayer;
    private Player whitePlayer;

    @BeforeEach
    public void setUp() {
        redPlayer = new Player("player 1");
        whitePlayer = new Player("player 2");
        CuT = new CheckersGame(redPlayer, whitePlayer);
    }

    @Test
    public void getBoard_test() {
        assertNotNull(CuT.getBoard());
    }

    @Test
    public void getRedBoardView_test() {
        assertNotNull(CuT.getRedBoardView());
    }

    @Test
    public void getWhiteBoardView_test() {
        assertNotNull(CuT.getWhiteBoardView());
    }

    @Test
    public void getRedPlayer_test() {
        assertSame(redPlayer, CuT.getRedPlayer());
    }

    @Test
    public void getWhitePlayer_test() {
        assertSame(whitePlayer, CuT.getWhitePlayer());
    }

    @Test
    public void getActiveColor_test() {
        assertNotNull(CuT.getActiveColor());
    }

    /*
    @Test
    public void saveAttemptedMove() {

    }
     */
}
