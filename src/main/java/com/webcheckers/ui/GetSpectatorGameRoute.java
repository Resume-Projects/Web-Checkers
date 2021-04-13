
package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Piece;
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

        Player spectatedPlayer = playerLobby.getPlayerFromName(request.queryParams("playerSpectated"));
        if(spectatedPlayer == null) {
            request.session().attribute("errorMessage", "The player you were spectating signed out");
            response.redirect("/");
            return null;
        }

        CheckersGame spectatedGame = gameManager.getPlayersGame(spectatedPlayer);
        if(!gameManager.isPlayerSpectating(currentUser)) {
            gameManager.addSpectator(spectatedGame.getGameID(), currentUser);
        }

        if(gameManager.getGameState(currentUser) != CheckersGame.State.PLAYING) {
            CheckersGame.State endGameState = gameManager.getGameState(currentUser);
            if(endGameState == CheckersGame.State.OVER) {
                request.session().attribute("errorMessage", "A player has captured all the pieces");
            } else if(endGameState == CheckersGame.State.ENDED) {
                request.session().attribute("errorMessage", "A player was not able to make any moves");
            } else {
                request.session().attribute("errorMessage", "A player has resigned from the game");
            }
            response.redirect("/");
            return null;
        }

        vm.put("title", "spectating " + spectatedPlayer.getName());
        vm.put("currentUser", currentUser);
        vm.put("viewMode", GetGameRoute.playMode.SPECTATOR);
        //Duplicate lines.........
        vm.put("redPlayer", new Player(spectatedGame.getRedPlayerName()));
        vm.put("whitePlayer", new Player(spectatedGame.getWhitePlayerName()));

        vm.put("activeColor", spectatedGame.getActiveColor());

        if(spectatedPlayer.equals(spectatedGame.getRedPlayer()))
            vm.put("board", spectatedGame.getRedBoardView());
        else
            vm.put("board", spectatedGame.getWhiteBoardView());

        String infoText = "You are spectating " + spectatedPlayer.getName() + ". This page will automatically refresh when a move is made";
        vm.put("message", Message.info(infoText));

        return new FreeMarkerEngine().render(new ModelAndView(vm, "game.ftl"));
    }
}
