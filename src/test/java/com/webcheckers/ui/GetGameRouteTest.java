package com.webcheckers.ui;


import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;
import org.junit.jupiter.api.Tag;
import spark.http.matching.Halt;

import static org.mockito.Mockito.*;

/**
 * Code coverage for GetGameRoute
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
    private CheckersGame checkersGame;

    private Player redPlayer;
    private Player whitePlayer;

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
    public void newGame() throws Exception {
        redPlayer = mock(Player.class);
        whitePlayer = mock(Player.class);
        checkersGame = new CheckersGame(redPlayer, whitePlayer);
        when(request.session().attribute("currentUser")).thenReturn(redPlayer);
        when(gameManager.getPlayersGame(redPlayer)).thenReturn(checkersGame);
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Game");
        testHelper.assertViewModelAttribute("redPlayer", checkersGame.getRedPlayer());
        testHelper.assertViewModelAttribute("whitePlayer", checkersGame.getWhitePlayer());

        testHelper.assertViewName(GetGameRoute.VIEW_NAME);
    }

    @Test
    public void testGameRed() throws Exception {
        redPlayer = mock(Player.class);
        whitePlayer = mock(Player.class);
        checkersGame = new CheckersGame(redPlayer, whitePlayer);
        when(session.attribute("currentUser")).thenReturn(redPlayer);
        // when(playerLobby.  thenReturn(whitePlayer);
        when(gameManager.getPlayersGame(redPlayer)).thenReturn(checkersGame);
        CuT.handle(request, response);
    }

    @Test
    public void testGameWhite() throws Exception {
        redPlayer = mock(Player.class);
        whitePlayer = mock(Player.class);
        checkersGame = new CheckersGame(redPlayer, whitePlayer);
        when(session.attribute("currentUser")).thenReturn(whitePlayer);
        // when(playerLobby.getPlayer("whitePlayer")).thenReturn(whitePlayer);
        when(gameManager.getPlayersGame(whitePlayer)).thenReturn(checkersGame);
        CuT.handle(request, response);
    }

    @Test
    public void faultySession() throws Exception {
        //Test for when neither player is in a game and a new game needs to be made
        redPlayer = mock(Player.class);
        whitePlayer = mock(Player.class);

        when(session.attribute("currentUser")).thenReturn(redPlayer);
        when(request.queryParams("whitePlayer")).thenReturn("white name");
        when(playerLobby.getPlayerFromName("white name")).thenReturn(whitePlayer);
        when(gameManager.getNewGame(redPlayer, whitePlayer)).thenReturn(mock(CheckersGame.class));
        //when(session.attribute("errorMessage"))
        CuT.handle(request, response);
        // assertThat(response).isEqualTo(null);
    }

    @Test
    public void whitePlayerAlreadyInGame() throws Exception {
        //If the white player is already in a game, then a new game should not be made
        when(request.queryParams("whitePlayer")).thenReturn("name");
        Player mockPlayer = mock(Player.class);
        when(playerLobby.getPlayerFromName("name")).thenReturn(mockPlayer);
        when(gameManager.getPlayersGame(mockPlayer)).thenReturn(mock(CheckersGame.class));
        CuT.handle(request, response);
        verify(response, times(1)).redirect("/");
    }

}



