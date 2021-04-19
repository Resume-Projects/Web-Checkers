package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.model.SavedGame;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;

/**
 * The UI Controller to GET the Game Replay page
 */
public class GetReplayGameRoute implements Route {

    /**
     * Attributes used
     */
    private final GameManager gameManager;
    private final Gson gson;

    /**
     * Create the Spark Route (UI Controller) to handle all {@code GET /replay/game} HTTP requests
     *
     * @param gameManager - stores all the information about the game replayed
     * @param gson - for viewing the game
     */
    public GetReplayGameRoute(final GameManager gameManager, final Gson gson) {
        this.gameManager = gameManager;
        this.gson = gson;
    }

    /**
     * Render the replay of the game
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the replay page
     */
    @Override
    public Object handle(Request request, Response response) {

        HashMap<String, Object> vm = new HashMap<>();
        HashMap<String, Object> modeOptions = new HashMap<>(2);
        vm.put("title", "Game Replay");
        if( request.session().attribute("currentUser") != null ) {
            final Player player = request.session().attribute("currentUser");
            final String gameID = request.queryParams("gameID");
            vm.put("currentUser", player);
            vm.put("viewMode", GetGameRoute.playMode.REPLAY);
            SavedGame savedGame = gameManager.getSavedGame(gameID);
            if( savedGame.getPlayerWatching() == null ) {
                savedGame.setPlayerWatching(player);
            } else if( !(savedGame.getPlayerWatching().equals(player)) && savedGame.getPlayerWatching() != null ) {
                request.session().attribute("errorMessage", "Error: Game is already being replayed");
                response.redirect("/");
                return null;
            }
            modeOptions.put("hasNext", savedGame.hasNext());
            modeOptions.put("hasPrevious", savedGame.hasPrevious());
            if( !savedGame.hasNext()) {
                modeOptions.put("isGameOver", true);
                modeOptions.put("gameOverMessage", "Game Over");
            }
            CheckersGame game = savedGame.getGame();
            vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
            vm.put("redPlayer", game.getRedPlayer());
            vm.put("whitePlayer", game.getWhitePlayer());
            vm.put("activeColor", game.getActiveColor());
            if( savedGame.getPlayerWatching() == game.getRedPlayer()){
                vm.put("board", game.getRedBoardView());
            }
            else {
                vm.put("board", game.getWhiteBoardView());
            }
        } else {
            final String gameID = request.queryParams("gameID");
            SavedGame savedGame = gameManager.getSavedGame(gameID);
            savedGame.setPlayerWatching(null);
            response.redirect(WebServer.HOME_URL);
            return null;
        }
        return new FreeMarkerEngine().render(new ModelAndView(vm, "game.ftl"));
    }
}
