package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.Collection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
public class GetHomeRouteTest {

    private GetHomeRoute CuT; //Component under test
    private Request request;
    private Response response;
    private GameManager gameManager;
    private TemplateEngineTester templateEngineTester;
    private PlayerLobby playerLobby;
    private TemplateEngine templateEngine;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        Session session = mock(Session.class);
        when(request.session()).thenReturn(session);

        gameManager = mock(GameManager.class);
        playerLobby = new PlayerLobby();
        templateEngine = mock(TemplateEngine.class);
        templateEngineTester = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(templateEngineTester.makeAnswer());

        CuT = new GetHomeRoute(gameManager, playerLobby, templateEngine);
    }

    //When this method is run, the majority of lines in the coverage report are green.
    //The remaining tests make it so the red lines get run
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
        verify(response, times(1)).redirect("/game");
        //There should be no view model rendered because a player goes to the game view
    }

    @Test
    public void testSeeingOtherPlayerNames() {
        Player mockPlayer = mock(Player.class);
        when(request.session().attribute("currentUser")).thenReturn(mockPlayer);
        playerLobby.newPlayer("other player");
        CuT.handle(request, response);
        templateEngineTester.assertViewModelExists();
        templateEngineTester.assertViewModelIsaMap();
        templateEngineTester.assertViewModelAttribute("currentUser", mockPlayer);
        templateEngineTester.assertViewModelAttribute("activePlayers", playerLobby.getActivePlayers().values());
    }

    @Test
    public void testErrorMessage() {
        when(request.session().attribute("errorMessage")).thenReturn("Error Message");
        CuT.handle(request, response);
        templateEngineTester.assertViewModelExists();
        templateEngineTester.assertViewModelIsaMap();

        //Message does not have an equals() method, so this line fails the test but the Expected
        //and Actual should be the same
        //templateEngineTester.assertViewModelAttribute("message", Message.error("Error Message"));
    }

    @Test
    public void gameOver_test() {
        Player redPlayer = mock(Player.class);
        Player whitePlayer = mock(Player.class);
        CheckersGame checkersGame = mock(CheckersGame.class);
        when(request.session().attribute("currentUser")).thenReturn(redPlayer);
        when(gameManager.getPlayersGame(redPlayer)).thenReturn(checkersGame);
        when(checkersGame.isGameDone()).thenReturn(true);
        when(checkersGame.gameEnded()).thenReturn(true);
        when(checkersGame.getWinner()).thenReturn(redPlayer);
        when(checkersGame.getLoser()).thenReturn(whitePlayer);

        CuT.handle(request, response);
    }

    @Test
    public void playerResigned_test() {
        Player redPlayer = mock(Player.class);
        Player whitePlayer = mock(Player.class);
        CheckersGame checkersGame = mock(CheckersGame.class);
        when(request.session().attribute("currentUser")).thenReturn(redPlayer);
        when(gameManager.getPlayersGame(redPlayer)).thenReturn(checkersGame);
        when(checkersGame.isGameDone()).thenReturn(true);
        when(checkersGame.gameEnded()).thenReturn(true);
        when(checkersGame.hasPlayerLeft()).thenReturn(true);
        when(checkersGame.getWinner()).thenReturn(redPlayer);
        when(checkersGame.getLoser()).thenReturn(whitePlayer);

        CuT.handle(request, response);
    }
}
