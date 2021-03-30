package com.webcheckers.application;

import com.webcheckers.model.Space;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Application-tier")
public class GameControllerTest {

    @Test
    public void test() {
        GameController CuT = new GameController();
        Space[][] board = new Space[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Space(i, Space.State.OPEN);
            }
        }
        GameController.initializeBoard(board);
    }
}
