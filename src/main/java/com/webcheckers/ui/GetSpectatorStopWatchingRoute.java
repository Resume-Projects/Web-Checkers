
package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetSpectatorStopWatchingRoute implements Route {

    public GetSpectatorStopWatchingRoute() {

    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        //request.queryParams("gameID") will give the game's ID
        return null;
    }
}
