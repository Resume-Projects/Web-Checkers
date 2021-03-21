package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import org.junit.jupiter.api.Tag;
import spark.TemplateEngine;

import static org.mockito.Mockito.*;

/**
 * Code coverage for GetHomeRoute
 *
 */
@Tag("UI-tier")
public class GetGameRouteTest {

    private GetGameRoute CuT; // Component under test
    private Request request;
    private Response response;
    private Session session;
    private GameManager gameManager;
    private PlayerLobby playerLobby;
    private TemplateEngine templateEngine;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        gameManager = mock(GameManager.class);
        playerLobby = mock(PlayerLobby.class);
        templateEngine = mock(TemplateEngine.class);
        CuT = new GetGameRoute(gameManager, playerLobby, templateEngine);

    }

    @Test
    public void testGame() throws Exception {
        try{
            CuT.handle(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
