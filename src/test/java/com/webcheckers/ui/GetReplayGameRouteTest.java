package com.webcheckers.ui;


import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.model.SavedGame;
import com.webcheckers.model.SavedMove;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;
import org.junit.jupiter.api.Tag;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

/**
 * Code coverage for GetGameRoute
 *
 */
@Tag("UI-tier")
public class GetReplayGameRouteTest {

    private GetReplayGameRoute CuT; // Component under test
    private Request request;
    private Response response;
    private Session session;
    private GameManager gameManager;
    private PlayerLobby playerLobby;
    private TemplateEngine templateEngine;
    private CheckersGame checkersGame;
    private Gson gson;
    private Player redPlayer;
    private Player whitePlayer;
    private SavedGame savedGame;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        gson = new Gson();
        gameManager = mock(GameManager.class);
        savedGame = new SavedGame(new ArrayList<>(), new Player("AAAAAA"), new Player("DASDAFSDSAD"), 5);
        when(gameManager.getSavedGame(any())).thenReturn(savedGame);
        when(session.attribute("currentUser")).thenReturn(new Player("REEEEEE"));
        playerLobby = mock(PlayerLobby.class);
        templateEngine = mock(TemplateEngine.class);
        CuT = new GetReplayGameRoute(gameManager, gson);
    }

    @Test
    public void doMostStuffTest() {
        CuT.handle(request, response);
    }

    @Test
    public void playerWatchingStuffTest() {
        savedGame = mock(SavedGame.class);
        when(savedGame.getPlayerWatching()).thenReturn(new Player("AGFSDDSA"));
        when(gameManager.getSavedGame(any())).thenReturn(savedGame);
        CuT.handle(request, response);
    }

    @Test
    public void noMoreMoves() {
        savedGame = mock(SavedGame.class);
        when(savedGame.hasNext()).thenReturn(false);
        when(savedGame.getGame()).thenReturn(new CheckersGame(new Player("@RADS"), new Player("@EREF"), 69));
        when(gameManager.getSavedGame(any())).thenReturn(savedGame);
        CuT.handle(request, response);
    }

    @Test
    public void getPlayerWatchingIsRedPlayer() {
        //No idea why this won't work
        savedGame = mock(SavedGame.class);
        when(savedGame.hasNext()).thenReturn(false);
        Player redPlayer = new Player("asd23D");
        when(savedGame.getGame()).thenReturn(new CheckersGame(redPlayer, redPlayer, 69));
        when(savedGame.getPlayerWatching()).thenReturn(redPlayer);
        when(gameManager.getSavedGame(any())).thenReturn(savedGame);
        CuT.handle(request, response);
    }

    @Test
    public void nullCurrentUser() {
        when(session.attribute("currentUser")).thenReturn(null);
        CuT.handle(request, response);
    }

}

