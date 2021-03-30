
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
import static org.mockito.Mockito.verify;

@Tag("UI-Tier")
public class PostSubmitTurnRouteTest {

    private PostSubmitTurnRoute CuT;
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
        CuT = new PostSubmitTurnRoute(gameManager);
    }

    @Test
    public void handleTest() throws Exception {
        Object uselessThing = CuT.handle(request, response);
    }

    @Test
    public void makeSureGameAppliesMove() throws Exception {
        CuT.handle(request, response);
        verify(checkersGame).applyAttemptedMoves();
    }

}