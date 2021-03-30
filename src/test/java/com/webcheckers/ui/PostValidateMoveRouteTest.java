package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.Position;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@Tag("UI-Tier")
public class PostValidateMoveRouteTest {

    private PostValidateMoveRoute CuT;
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
        when(gameManager.getPlayersGame(any(Player.class))).thenReturn(checkersGame);
        when(checkersGame.saveAttemptedMove(any(Move.class))).thenReturn(Message.info("Valid Move"));
        CuT = new PostValidateMoveRoute(gameManager);
    }

    @Test
    public void handleTest() throws Exception {
        Object uselessThing = CuT.handle(request, response);
    }

    @Test
    public void checkReturnedJson() throws Exception {
        Position start = new Position(8, 8);
        Position end = new Position(7, 7);
        when(request.queryParams("actionData")).thenReturn(new Gson().toJson(new Move(start, end)));
        Object handleReturn = CuT.handle(request, response);
        assertTrue(handleReturn.toString().contains("Valid Move"));
    }

}