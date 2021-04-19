package com.webcheckers.ui;


import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.*;
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
public class PostNextTurnRouteTest {

    private PostNextTurnRoute CuT; // Component under test
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

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        gameManager = mock(GameManager.class);
        playerLobby = mock(PlayerLobby.class);
        templateEngine = mock(TemplateEngine.class);
        gson = new Gson();
        CuT = new PostNextTurnRoute(gameManager, gson);

    }

    @Test
    public void almostEverything() {
        ArrayList<SavedMove> arrayList = new ArrayList<>();
        arrayList.add(new SavedMove(new Space[8][8], Piece.Color.RED));
        arrayList.add(new SavedMove(new Space[8][8], Piece.Color.RED));
        when(gameManager.getSavedGame(any())).thenReturn(new SavedGame(arrayList, new Player("ASD"), new Player("ASDSA"), 69));
        CuT.handle(request, response);
    }

    @Test
    public void otherThing() {
        ArrayList<SavedMove> arrayList = new ArrayList<>();
        arrayList.add(new SavedMove(new Space[8][8], Piece.Color.RED));
        arrayList.add(new SavedMove(new Space[8][8], Piece.Color.RED));
        SavedGame savedGame = mock(SavedGame.class);
        when(savedGame.getTurnNum()).thenReturn(420);
        when(gameManager.getSavedGame(any())).thenReturn(savedGame);
        CuT.handle(request, response);
    }

}

