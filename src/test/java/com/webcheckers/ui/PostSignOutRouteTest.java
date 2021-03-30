
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

import static org.mockito.Mockito.*;

@Tag("UI-Tier")
public class PostSignOutRouteTest {

    private PostSignOutRoute CuT;
    private Request request;
    private Response response;
    private Session session;
    private PlayerLobby playerlobby;
    private TemplateEngine templateEngine;
    private GameManager gameManager;
    private Player player1;
    private CheckersGame checkersGame;


    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        playerlobby = mock(PlayerLobby.class);
        checkersGame = mock(CheckersGame.class);
        player1 = new Player("Player 1");
        when(session.attribute("currentUser")).thenReturn(player1);
        session.attribute("currentUser", "Player 1");
        when(request.session()).thenReturn(session);
        templateEngine = mock(TemplateEngine.class);
        gameManager = mock(GameManager.class);
        when(gameManager.getPlayersGame(player1)).thenReturn(checkersGame);
        CuT = new PostSignOutRoute(playerlobby, gameManager);
    }

    @Test
    public void handleTest() {
        Object uselessThing = CuT.handle(request, response);
    }

    @Test
    public void nameIsRemovedTest() {
        CuT.handle(request, response);
        verify(session).attribute("currentUser", null);
    }

    @Test
    public void GameGetsResignedTest() {
        CuT.handle(request, response);
        verify(checkersGame).resignGame(player1);
    }
}
