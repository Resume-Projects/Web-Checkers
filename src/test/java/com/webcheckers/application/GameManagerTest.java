package com.webcheckers.application;

import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;


@Tag("Application-tier")
public class GameManagerTest {

    private GameManager CuT;

    @BeforeEach
    public void setup() {
        CuT = new GameManager();
    }

    @Test
    public void largeTest() {
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

    @Test
    public void deleteGame_test() {
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        CheckersGame game = CuT.getNewGame(player1, player2);

        CuT.deleteGame(player1);
        assertNull(CuT.getPlayersGame(player1));
    }

    @Test
    public void gameHasBeenUpdated_test() {
        Player red = new Player("player 1");
        Player white = new Player("player 2");
        Player spectator = new Player("player 3");
        CuT.getNewGame(red, white);
        CuT.addSpectator(0, spectator);

        CuT.gameHasBeenUpdated(0);
        assertSame(CuT.getGameState(spectator), CheckersGame.State.PLAYING);
    }

    @Test
    public void getActiveGames_test() {
        Player red = new Player("player 1");
        Player white = new Player("player 2");
        CuT.getNewGame(red, white);

        assertNotNull(CuT.getActiveGames());
    }

    @Test
    public void addSpectator_test() {
        Player red = new Player("player 1");
        Player white = new Player("player 2");
        CuT.getNewGame(red, white);

        Player spectator = new Player("player 3");
        CuT.addSpectator(0, spectator);
        assertTrue(CuT.isPlayerSpectating(spectator));
    }

    @Test
    public void playerNotSpectating_test() {
        Player red = new Player("player 1");
        Player white = new Player("player 2");
        CuT.getNewGame(red, white);

        Player spectator = new Player("player 3");
        assertFalse(CuT.isPlayerSpectating(spectator));
    }

    @Test
    public void removeSpectator_test() {
        Player red = new Player("player 1");
        Player white = new Player("player 2");
        CuT.getNewGame(red, white);

        Player spectator = new Player("player 3");
        CuT.addSpectator(0, spectator);

        CuT.removeSpectator(spectator);
        assertFalse(CuT.isPlayerSpectating(spectator));
    }

    @Test
    public void boardHasBeenUpdated_test() {
        Player red = new Player("player 1");
        Player white = new Player("player 2");
        Player spectator = new Player("player 3");
        CuT.getNewGame(red, white);
        CuT.addSpectator(0, spectator);

        CuT.gameHasBeenUpdated(0);
        assertTrue(CuT.hasBoardBeenUpdated(spectator));
    }

    @Test
    public void boardHasNotBeenUpdated_test() {
        Player red = new Player("player 1");
        Player white = new Player("player 2");
        Player spectator = new Player("player 3");
        CuT.getNewGame(red, white);
        CuT.addSpectator(0, spectator);

        assertFalse(CuT.hasBoardBeenUpdated(spectator));
    }

    @Test
    public void getRedPlayersColor_test() {
        Player red = new Player("player 1");
        Player white = new Player("player 2");
        CuT.getNewGame(red, white);

        assertSame(CuT.getPlayersColor(red), Piece.Color.RED);
    }

    @Test
    public void getWhitePlayersColor_test() {
        Player red = new Player("player 1");
        Player white = new Player("player 2");
        CuT.getNewGame(red, white);

        assertSame(CuT.getPlayersColor(white), Piece.Color.WHITE);
    }

    @Test
    public void getNullPlayersColor_test() {
        Player red = new Player("player 1");
        Player white = new Player("player 2");
        CheckersGame game = new CheckersGame(red, white, 0);

        assertNull(CuT.getPlayersColor(red));
    }
}
