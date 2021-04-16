package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
public class PostSpectatorCheckTurnRouteTest {
    private PostSpectatorCheckTurnRoute CuT;
    private GameManager gameManager;
    private Request request;
    private Response response;
    private Session session;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        gameManager = mock(GameManager.class);

        CuT = new PostSpectatorCheckTurnRoute(gameManager);
    }

    @Test
    public void handleTrue_test() {
        Player player = new Player("player 1");
        when(request.session().attribute("currentUser")).thenReturn(player);
        when(gameManager.hasBoardBeenUpdated(player)).thenReturn(true);

        CuT.handle(request, response);
    }

    @Test
    public void handleFalse_test() {
        Player player = new Player("player 1");
        when(request.session().attribute("currentUser")).thenReturn(player);
        when(gameManager.hasBoardBeenUpdated(player)).thenReturn(false);

        CuT.handle(request, response);
    }
}
