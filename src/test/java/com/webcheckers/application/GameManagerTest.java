package com.webcheckers.application;

import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@Tag("Application-tier")
public class GameManagerTest {

    private GameManager CuT;

    @BeforeEach
    public void setup() {
        CuT = new GameManager();
    }

    @Test
    public void testingEverything() {
        Player mockPlayer1 = mock(Player.class);
        Player mockPlayer2 = mock(Player.class);
        assertNotNull(CuT.getNewGame(mockPlayer1, mockPlayer2));
        assertNotNull(CuT.getPlayersGame(mockPlayer1));
        assertNull(CuT.getPlayersGame(mock(Player.class)));
    }

    @Test
    public void resignGame_test() {
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        CheckersGame game = CuT.getNewGame(player1, player2);

        assertTrue(CuT.resignGame(player1));
    }

}
