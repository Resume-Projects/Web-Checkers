
package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetSpectatorGameRoute implements Route {

    public GetSpectatorGameRoute() {

    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        //request.queryParams("gameID") will give the game's ID
        return null;
    }
}
