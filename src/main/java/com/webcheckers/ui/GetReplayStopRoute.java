package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.model.Player;
import com.webcheckers.model.SavedGame;
import spark.*;
import java.util.HashMap;

/**
 * The UI Controller to GET the route to stop watching (exit) the replay
 */
public class GetReplayStopRoute implements Route {

    private GameManager gameManager;

    /**
     * Create the Spark Route (UI Controller) to handle all {@code GET /replay/}
     *
     * @param gameManager stores information about the game being replayed
     */
    public GetReplayStopRoute(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Resets the replay and sends the user back to the home page
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @return null... send the user back to the homepage
     */
    @Override
    public Object handle(Request request, Response response) {
        HashMap<String, Object> vm = new HashMap<>();
        vm.put("title", "Exit Replay Mode");
        Player currentUser = request.session().attribute("playerWatching");
        SavedGame savedGame = gameManager.getSavedGame(request.queryParams("gameID"));
        savedGame.setTurnNum(0);
        savedGame.setPlayerWatching(null);
        savedGame.updateBoard();
        vm.put("currentUser", currentUser);
        response.redirect(WebServer.HOME_URL);
        return null;
    }
}
