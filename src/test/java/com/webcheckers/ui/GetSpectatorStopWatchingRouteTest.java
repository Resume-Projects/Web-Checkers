package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
public class GetSpectatorStopWatchingRouteTest {
    private GetSpectatorStopWatchingRoute CuT;
    private GameManager gameManager;
    private Request request;
    private Response response;
    private Session session;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        gameManager = mock(GameManager.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        CuT = new GetSpectatorStopWatchingRoute(gameManager);
    }

    @Test
    public void handle_test() {
        Player spectator = new Player("player 1");
        when(request.session().attribute("currentUser")).thenReturn(spectator);

        assertNull(CuT.handle(request, response));
    }
}
