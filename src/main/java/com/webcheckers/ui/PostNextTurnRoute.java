package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.model.SavedGame;
import com.webcheckers.util.Message;
import spark.*;

public class PostNextTurnRoute implements Route{

    private GameManager gameManager;
    private Gson gson;

    public PostNextTurnRoute(GameManager gameManager, Gson gson) {
        this.gameManager = gameManager;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        SavedGame savedGame = gameManager.getSavedGame(request.queryParams("gameID"));
        int currentTurn = savedGame.getTurnNum();
        savedGame.nextTurn();
        if( savedGame.getTurnNum() > savedGame.getSavedMoves().size()) {
            savedGame.setTurnNum(savedGame.getSavedMoves().size() - 1);
            return gson.toJson(Message.error("Error in going to next turn"));
        }
        else {
            return gson.toJson(Message.info("true"));
        }
    }
}
