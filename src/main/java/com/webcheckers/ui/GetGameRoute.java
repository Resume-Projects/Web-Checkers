package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
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

    /**
     * The different play modes that a user can be in
     */
    public enum playMode {PLAY, SPECTATOR, REPLAY}

    /**
     * The two different colors a player can be
     */

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final GameManager gameManager;
    private final Gson gson;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public GetGameRoute(final GameManager gameManager, final PlayerLobby playerLobby, final TemplateEngine templateEngine, final Gson gson) {
        this.gameManager = gameManager;
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        this.gson = gson;
    }

    /**
     * Render the WebCheckers Game page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Game page
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session session = request.session();
        Map<String, Object> vm = new HashMap<>();
        Map<String, Object> modeOptions = new HashMap<>();
        Player currentPlayer = session.attribute("currentUser");
        CheckersGame checkersGame;

        //If the player is not in a game, then the game just started and a game needs to be made
        if (gameManager.getPlayersGame(currentPlayer) == null) {
            Player whitePlayer = playerLobby.getPlayerFromName(request.queryParams("whitePlayer"));
            //If the white player is already in a game, send them back to the home page
            if(gameManager.getPlayersGame(whitePlayer) != null) {
                session.attribute("errorMessage", "That player is already in a game");
                response.redirect("/");
                return null;
            } else if(gameManager.isPlayerSpectating(whitePlayer)) {
                session.attribute("errorMessage", "That player is currently spectating a game");
                response.redirect("/");
                return null;
            } else {
                checkersGame = gameManager.getNewGame(currentPlayer, whitePlayer);
                gameManager.gameHasBeenUpdated(checkersGame.getGameID());
            }
        } else {
            checkersGame = gameManager.getPlayersGame(currentPlayer);
        }
        if(checkersGame.getIsGameDone()) {
            modeOptions.put("isGameOver", true);
            vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
            if(checkersGame.isResigned()) {
                vm.put("message", Message.info(String.format("%s has resigned, %s has won the game.",
                        checkersGame.getLoser().getName(), checkersGame.getWinner().getName())));
            } else if(checkersGame.gameEnded()) {
                gameManager.saveGame(checkersGame.getGameID(), currentPlayer);
                vm.put("message", Message.info(String.format("%s has captured all of the pieces.",
                        checkersGame.getWinner().getName())));
            } else { //A player is unable to move
                gameManager.saveGame(checkersGame.getGameID(), currentPlayer);
                vm.put("message", Message.info(String.format("%s is unable to move.",
                        checkersGame.getLoser().getName())));
            }
        }

        vm.put("title", "Game");
        vm.put("currentUser", session.attribute("currentUser"));
        vm.put("viewMode", playMode.PLAY);
        //These make new Players because the players could be null.
        //Getting the players name will never be null
        vm.put("redPlayer", new Player(checkersGame.getRedPlayerName()));
        vm.put("whitePlayer", new Player(checkersGame.getWhitePlayerName()));
        vm.put("activeColor", checkersGame.getActiveColor());
        vm.put("gameID", checkersGame.getGameID());

        if (currentPlayer.equals(checkersGame.getRedPlayer()))
            vm.put("board", checkersGame.getRedBoardView());
        else
            vm.put("board", checkersGame.getWhiteBoardView());


        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
