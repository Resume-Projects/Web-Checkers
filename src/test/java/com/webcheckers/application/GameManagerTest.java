package com.webcheckers.application;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

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


}
