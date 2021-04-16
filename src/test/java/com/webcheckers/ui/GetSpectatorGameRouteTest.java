package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("UI-Tier")
public class GetSpectatorGameRouteTest {
    private GetSpectatorGameRoute CuT;
    private Request request;
    private Response response;
    private Session session;
    private PlayerLobby playerLobby;
    private GameManager gameManager;
    private TemplateEngine templateEngine;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        playerLobby = mock(PlayerLobby.class);
        gameManager = mock(GameManager.class);
        templateEngine = mock(TemplateEngine.class);

        CuT = new GetSpectatorGameRoute(playerLobby, gameManager, templateEngine);
    }

    @Test
    public void nullGame_test() {
        Player redPlayer = new Player("Player 1");
        Player spectator = new Player("player 3");
        when(request.session().attribute("currentUser")).thenReturn(spectator);
        when(request.queryParams("playerSpectated")).thenReturn(redPlayer.getName());

        assertNull(CuT.handle(request, response));
    }

    @Test
    public void alreadySpectating_test() {
        Player redPlayer = new Player("player 1");
        Player whitePlayer = new Player("player 2");
        Player spectator = new Player("player 3");
        CheckersGame game = new CheckersGame(redPlayer, whitePlayer, 0);
        when(gameManager.getNewGame(redPlayer, whitePlayer)).thenReturn(game);
        when(request.session().attribute("currentUser")).thenReturn(spectator);
        when(playerLobby.getPlayerFromName(request.queryParams("playerSpectated"))).thenReturn(redPlayer);
        when(gameManager.isPlayerSpectating(spectator)).thenReturn(true);
        when(gameManager.getPlayersGame(redPlayer)).thenReturn(game);
        when(gameManager.getGameState(spectator)).thenReturn(CheckersGame.State.PLAYING);

        assertNotNull(CuT.handle(request, response));
    }

    @Test
    public void spectatingWhite_test() {
        Player redPlayer = new Player("player 1");
        Player whitePlayer = new Player("player 2");
        Player spectator = new Player("player 3");
        CheckersGame game = new CheckersGame(redPlayer, whitePlayer, 0);
        when(gameManager.getNewGame(redPlayer, whitePlayer)).thenReturn(game);
        when(request.session().attribute("currentUser")).thenReturn(spectator);
        when(playerLobby.getPlayerFromName(request.queryParams("playerSpectated"))).thenReturn(whitePlayer);
        when(gameManager.isPlayerSpectating(spectator)).thenReturn(true);
        when(gameManager.getPlayersGame(whitePlayer)).thenReturn(game);
        when(gameManager.getGameState(spectator)).thenReturn(CheckersGame.State.PLAYING);

        assertNotNull(CuT.handle(request, response));
    }

    @Test
    public void addSpectator_test() {
        Player redPlayer = new Player("player 1");
        Player whitePlayer = new Player("player 2");
        Player spectator = new Player("player 3");
        CheckersGame game = new CheckersGame(redPlayer, whitePlayer, 0);
        when(gameManager.getNewGame(redPlayer, whitePlayer)).thenReturn(game);
        when(request.session().attribute("currentUser")).thenReturn(spectator);
        when(playerLobby.getPlayerFromName(request.queryParams("playerSpectated"))).thenReturn(redPlayer);
        when(gameManager.isPlayerSpectating(spectator)).thenReturn(false);
        when(gameManager.getPlayersGame(redPlayer)).thenReturn(game);
        when(gameManager.getGameState(spectator)).thenReturn(CheckersGame.State.PLAYING);

        assertNotNull(CuT.handle(request, response));
    }

    @Test
    public void stateOver_test() {
        Player redPlayer = new Player("player 1");
        Player whitePlayer = new Player("player 2");
        Player spectator = new Player("player 3");
        CheckersGame game = new CheckersGame(redPlayer, whitePlayer, 0);
        when(gameManager.getNewGame(redPlayer, whitePlayer)).thenReturn(game);
        when(request.session().attribute("currentUser")).thenReturn(spectator);
        when(playerLobby.getPlayerFromName(request.queryParams("playerSpectated"))).thenReturn(redPlayer);
        when(gameManager.isPlayerSpectating(spectator)).thenReturn(true);
        when(gameManager.getPlayersGame(redPlayer)).thenReturn(game);
        when(gameManager.getGameState(spectator)).thenReturn(CheckersGame.State.OVER);

        assertNull(CuT.handle(request, response));
    }

    @Test
    public void stateEnded_test() {
        Player redPlayer = new Player("player 1");
        Player whitePlayer = new Player("player 2");
        Player spectator = new Player("player 3");
        CheckersGame game = new CheckersGame(redPlayer, whitePlayer, 0);
        when(gameManager.getNewGame(redPlayer, whitePlayer)).thenReturn(game);
        when(request.session().attribute("currentUser")).thenReturn(spectator);
        when(playerLobby.getPlayerFromName(request.queryParams("playerSpectated"))).thenReturn(redPlayer);
        when(gameManager.isPlayerSpectating(spectator)).thenReturn(true);
        when(gameManager.getPlayersGame(redPlayer)).thenReturn(game);
        when(gameManager.getGameState(spectator)).thenReturn(CheckersGame.State.ENDED);

        assertNull(CuT.handle(request, response));
    }

    @Test
    public void stateResigned_test() {
        Player redPlayer = new Player("player 1");
        Player whitePlayer = new Player("player 2");
        Player spectator = new Player("player 3");
        CheckersGame game = new CheckersGame(redPlayer, whitePlayer, 0);
        when(gameManager.getNewGame(redPlayer, whitePlayer)).thenReturn(game);
        when(request.session().attribute("currentUser")).thenReturn(spectator);
        when(playerLobby.getPlayerFromName(request.queryParams("playerSpectated"))).thenReturn(redPlayer);
        when(gameManager.isPlayerSpectating(spectator)).thenReturn(true);
        when(gameManager.getPlayersGame(redPlayer)).thenReturn(game);
        when(gameManager.getGameState(spectator)).thenReturn(CheckersGame.State.RESIGNED);

        assertNull(CuT.handle(request, response));
    }
}
