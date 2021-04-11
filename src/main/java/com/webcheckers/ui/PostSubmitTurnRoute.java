package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Class that handles players submitting their turns
 */
public class PostSubmitTurnRoute implements Route {

    private final GameManager gameManager;

    /**
     * Constructor for the PostSubmitTurnRoute. Should only get called once by the Webserver class
     * @param gameManager the GameManager that handles all the active games
     */
    public PostSubmitTurnRoute(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Player currentPlayer = request.session().attribute("currentUser");
        CheckersGame playersGame = gameManager.getPlayersGame(currentPlayer);
        gameManager.gameHasBeenUpdated(playersGame);
        return new Gson().toJson(playersGame.applyAttemptedMoves());
    }
}
