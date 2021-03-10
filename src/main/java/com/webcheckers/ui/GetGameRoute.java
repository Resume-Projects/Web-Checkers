package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;
import static spark.Spark.halt;

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

    public enum playMode {PLAY, SPECTATOR, REPLAY}
    public enum playerColor {RED, WHITE};

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private CheckersGame checkersGame;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetGameRoute (CheckersGame checkersGame, final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        this.checkersGame = checkersGame;
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
        if(checkersGame.getRedPlayer() == null) {
            Player redPlayer = session.attribute("currentUser");
            Player whitePlayer = playerLobby.getPlayerFromName(request.queryParams("whitePlayer"));
            checkersGame.setRedPlayer(redPlayer);
            checkersGame.setWhitePlayer(whitePlayer);
        } else {
            if(!currentPlayer.equals(checkersGame.getRedPlayer()) && !currentPlayer.equals(checkersGame.getWhitePlayer())) {
                session.attribute("errorMessage", "That player is already in a game");
                response.redirect("/");
            }
        }

        vm.put("title", "Game");
        vm.put("currentUser", session.attribute("currentUser"));
        vm.put("viewMode", playMode.PLAY);
        vm.put("redPlayer", checkersGame.getRedPlayer());
        vm.put("whitePlayer", checkersGame.getWhitePlayer());
        if(checkersGame.getRedPlayer().equals(session.attribute("currentUser")))
            vm.put("activeColor", playerColor.RED);
        else
            vm.put("activeColor", playerColor.WHITE);

        if(currentPlayer.equals(checkersGame.getRedPlayer()))
            vm.put("board", checkersGame.getRedBoardView());
        else
            vm.put("board", checkersGame.getWhiteBoardView());

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
