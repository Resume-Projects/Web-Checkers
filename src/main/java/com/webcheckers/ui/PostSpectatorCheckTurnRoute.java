
package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * The UI Controller to POST a check turn for the spectators screen
 */
public class PostSpectatorCheckTurnRoute implements Route {

    private final GameManager gameManager;

    /**
     * Create the Spark Route (UI Controller) to handle all {@code POST /spectator/checkTurn} HTTP requests.
     *
     * @param gameManager
     *   Stores information pertaining to the given game
     */
    public PostSpectatorCheckTurnRoute(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Gets the updated view of the board for the spectator after a turn has been made
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     * @return
     *   the new rendered HTML for the updated board after the turn has been made
     */
    @Override
    public Object handle(Request request, Response response) {
        //request.queryParams("gameID") will give the game's ID
        Player currentUser = request.session().attribute("currentUser");
        return new Gson().toJson(Message.info("" + gameManager.hasBoardBeenUpdated(currentUser)));
    }
}
