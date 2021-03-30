package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.FieldSetter;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
public class PostResignGameRouteTest {
    private PostResignGameRoute CuT;
    private GameManager gameManager;

    private Request request;
    private Response response;
    private Session session;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setUp() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        player1 = new Player("player 1");
        player2 = new Player("player 2");
        gameManager = new GameManager();

        when(request.session()).thenReturn(session);
        when(request.session().attribute("currentUser")).thenReturn(player1);

        CuT = new PostResignGameRoute(gameManager);
    }

    @Test
    public void handle_test() throws Exception {
        gameManager.getNewGame(player1, player2);
        CuT.handle(request, response);
    }

    @Test
    public void resignFailedHandle_test() throws Exception {
        CheckersGame game = gameManager.getNewGame(player1, player2);
        FieldSetter.setField(game, game.getClass().getDeclaredField("activeColor"), null);
        CuT.handle(request, response);
    }
}
