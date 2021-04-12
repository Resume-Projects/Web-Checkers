
package com.webcheckers.ui;

import com.webcheckers.application.GameManager;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;

public class GetSpectatorStopWatchingRoute implements Route {

    private final GameManager gameManager;

    public GetSpectatorStopWatchingRoute(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        //request.queryParams("gameID") will give the game's ID
        Player currentUser = request.session().attribute("currentUser");
        gameManager.removeSpectator(currentUser);
        response.redirect("/");
        return null;
    }
}
