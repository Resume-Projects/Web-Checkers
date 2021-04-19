package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.model.SavedGame;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * The UI Controller to POST the next turn of the replay
 */
public class PostNextTurnRoute implements Route{

    /**
     * Attributes
     */
    private GameManager gameManager;
    private Gson gson;

    /**
     * Create the Spark Route (UI Controller) to handle all {@code POST /replay/nextTurn} HTTP requests
     *
     * @param gameManager stores information about the game at hand
     * @param gson to view the turn
     */
    public PostNextTurnRoute(GameManager gameManager, Gson gson) {
        this.gameManager = gameManager;
        this.gson = gson;
    }

    /**
     * Renders the previous turn of the game being replayed
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the new rendered HTML for the updated board after the turn has been made
     */
    @Override
    public Object handle(Request request, Response response) {

        SavedGame savedGame = gameManager.getSavedGame(request.queryParams("gameID"));
        int currentTurn = savedGame.getTurnNum();
        savedGame.setTurnNum(currentTurn + 1);
        savedGame.updateBoard();
        if( savedGame.getTurnNum() > savedGame.getSavedMoves().size()) {
            savedGame.setTurnNum(savedGame.getSavedMoves().size() - 1);
            return gson.toJson(Message.error("Error in going to next turn"));
        }
        else {
            return gson.toJson(Message.info("true"));
        }
    }
}
