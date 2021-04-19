package com.webcheckers.ui;


import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;
import org.junit.jupiter.api.Tag;

import static org.mockito.Mockito.*;

/**
 * Code coverage for GetGameRoute
 *
 */
@Tag("UI-tier")
public class WebServerTest {

    private WebServer CuT; // Component under test
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
        CuT = new WebServer(gameManager, playerLobby, templateEngine, gson);

    }

    @Test
    public void newGame() throws Exception {
        CuT.initialize();
    }

}



