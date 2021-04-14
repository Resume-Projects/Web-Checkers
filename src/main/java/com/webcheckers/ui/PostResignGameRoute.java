package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Class that handles players resigning from games
 */
public class PostResignGameRoute implements Route {

    private final GameManager gameManager;

    /**
     * Constructor for the PostResignGameRoute. Should only be called once by the Webserver class
     * @param gameManager the GameManager that handles all the active games
     */
    public PostResignGameRoute(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Player player = request.session().attribute("currentUser");
        CheckersGame playersGame = gameManager.getPlayersGame(player);
        boolean resign = playersGame.resignGame(player);
        gameManager.gameHasBeenUpdated(playersGame.getGameID());
        if(resign) {
            return new Gson().toJson(Message.info(player.getName() + "has resigned."));
        }
        return new Gson().toJson(Message.error("Resign failed"));
    }
}
