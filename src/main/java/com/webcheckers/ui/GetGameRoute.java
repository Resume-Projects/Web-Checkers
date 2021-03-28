package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;

import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

/**
 * The UI Controller to GET the Game page.
 */
public class GetGameRoute implements Route {
    // Values used in the view-model for rendering the game view.
    static final String TITLE = "Checkers Game";
    static final String VIEW_NAME = "game.ftl";
    static final String RED_PLAYER = "redPlayer";
    static final String WHITE_PLAYER = "whitePlayer";
    static final String ACTIVE_COLOR = "activeColor";
    static final String BOARD = "board";
    static final String MESSAGE = "message";

    /** The different play modes that a user can be in */
    public enum playMode {PLAY, SPECTATOR, REPLAY}
    /** The two different colors a player can be */
    public enum playerColor {RED, WHITE};

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final GameManager gameManager;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetGameRoute(final GameManager gameManager, final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        this.gameManager = gameManager;
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
    }

    /**
     * Render the WebCheckers Game page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Game page
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        Map<String, Object> vm = new HashMap<>();
        Player currentPlayer = session.attribute("currentUser");
        CheckersGame checkersGame;

        //If the player is not in a game, then the game just started and a game needs to be made
        if(gameManager.getPlayersGame(currentPlayer) == null) {
            Player whitePlayer = playerLobby.getPlayerFromName(request.queryParams("whitePlayer"));
            //If the white player is already in a game, send them back to the home page
            if(gameManager.getPlayersGame(whitePlayer) != null) {
                session.attribute("errorMessage", "That player is already in a game");
                response.redirect("/");
                return null; //They get sent back to the home page
            } else {
                checkersGame = gameManager.getNewGame(currentPlayer, whitePlayer);
            }
        } else {
            checkersGame = gameManager.getPlayersGame(currentPlayer);
        }
        if(checkersGame.isResigned()) {
            vm.put("message", Message.info(String.format("%s has resigned, %s has won the game.",
                    checkersGame.getLoser().getName(), checkersGame.getWinner().getName())));
            response.redirect("/");
            return null;
        }

        vm.put("title", "Game");
        vm.put("currentUser", session.attribute("currentUser"));
        vm.put("viewMode", playMode.PLAY);
        vm.put("redPlayer", checkersGame.getRedPlayer());
        vm.put("whitePlayer", checkersGame.getWhitePlayer());
        vm.put("activeColor", checkersGame.getActiveColor());

        if(currentPlayer.equals(checkersGame.getRedPlayer()))
            vm.put("board", checkersGame.getRedBoardView());
        else
            vm.put("board", checkersGame.getWhiteBoardView());

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
