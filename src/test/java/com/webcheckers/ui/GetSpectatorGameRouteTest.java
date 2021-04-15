package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.assertNull;
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
        Player spectator = new Player("player 3");

        when(request.session().attribute("currentUser")).thenReturn(spectator);

        assertNull(CuT.handle(request, response));
    }

    @Test
    public void spectateGame_test() {
        Player redPlayer = new Player("player 1");
        Player whitePlayer = new Player("player 2");
        Player spectator = new Player("player 3");
        CheckersGame checkersGame = new CheckersGame(redPlayer, whitePlayer, 1);
    }
}
