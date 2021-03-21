package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
public class GetHomeRouteTest {

    private GetHomeRoute CuT; //Component under test
    private Request request;
    private Response response;
    private GameManager gameManager;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        Session session = mock(Session.class);
        when(request.session()).thenReturn(session);

        gameManager = mock(GameManager.class);
        PlayerLobby playerLobby = mock(PlayerLobby.class);
        TemplateEngine templateEngine = mock(TemplateEngine.class);

        CuT = new GetHomeRoute(gameManager, playerLobby, templateEngine);
    }

    @Test
    public void testMostThings() {
        CuT.handle(request, response);
    }

    @Test
    public void testPlayerInGame() {
        Player mockPlayer = mock(Player.class);
        when(request.session().attribute("currentUser")).thenReturn(mockPlayer);
        when(gameManager.getPlayersGame(mockPlayer)).thenReturn(mock(CheckersGame.class));
        CuT.handle(request, response);
    }

    @Test
    public void testSeeingOtherPlayerNames() {
        when(request.session().attribute("currentUser")).thenReturn(mock(Player.class));
        CuT.handle(request, response);
    }

    @Test
    public void testErrorMessage() {
        when(request.session().attribute("errorMessage")).thenReturn("Error Message");
        CuT.handle(request, response);
    }

}
