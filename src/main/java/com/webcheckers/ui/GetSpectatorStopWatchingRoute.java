
package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * The UI Controller to GET the Stop Watching route
 */
public class GetSpectatorStopWatchingRoute implements Route {

    private final GameManager gameManager;

    /**
     * Create the Spark Route (UI Controller) to handle all {@code GET /spectator/stopWatching} HTTP requests
     *
     * @param gameManager
     *   stores information pertaining to the given game
     */
    public GetSpectatorStopWatchingRoute(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Sends the user back to the home page when they decide to stop spectating the given game
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     * @return
     *   null, with a redirect back to the home page
     */
    @Override
    public Object handle(Request request, Response response) {
        //request.queryParams("gameID") will give the game's ID
        Player currentUser = request.session().attribute("currentUser");
        gameManager.removeSpectator(currentUser);
        response.redirect("/");
        return null;
    }
}
