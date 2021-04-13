
package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;

/**
 * The UI Controller to GET the Game page for the spectator.
 */
public class GetSpectatorGameRoute implements Route {


    private final PlayerLobby playerLobby;
    private final GameManager gameManager;
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI Controller) to handle all {@code GET /spectator/game} HTTP requests.
     *
     * @param playerLobby
     *   stores the current players
     * @param gameManager
     *   stores information pertaining to the given game
     * @param templateEngine
     *   tempalte engine to use for rendering HTML page
     */
    public GetSpectatorGameRoute(PlayerLobby playerLobby, GameManager gameManager, TemplateEngine templateEngine) {
        this.playerLobby = playerLobby;
        this.gameManager = gameManager;
        this.templateEngine = templateEngine;
    }

    /**
     * Render the WebCheckers game page for a spectator
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     * @return
     *   the rendered HTML for the game being spectated
     */
    @Override
    public Object handle(Request request, Response response) {
        //request.queryParams("gameID") will give the game's ID
        HashMap<String, Object> vm = new HashMap<>();
        Player currentUser = request.session().attribute("currentUser");
        //This should maybe be used
        //int gameID = Integer.parseInt(request.queryParams("gameID"));
        Player spectatedPlayer = playerLobby.getPlayerFromName(request.queryParams("playerSpectated"));
        CheckersGame spectatedGame = gameManager.getPlayersGame(spectatedPlayer);

        vm.put("title", "spectating");
        vm.put("currentUser", currentUser);
        vm.put("viewMode", GetGameRoute.playMode.SPECTATOR);
        vm.put("redPlayer", new Player(spectatedGame.getRedPlayerName()));
        vm.put("whitePlayer", new Player(spectatedGame.getWhitePlayerName()));
        vm.put("activeColor", spectatedGame.getActiveColor());
        vm.put("board", spectatedGame.getRedBoardView());

        vm.put("message", Message.info("You are spectating"));

        return new FreeMarkerEngine().render(new ModelAndView(vm, "game.ftl"));
    }
}
