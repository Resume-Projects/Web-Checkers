package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameManager;
import com.webcheckers.model.Player;
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

        Player currentUser = request.session().attribute("playerWatching");
        SavedGame savedGame = gameManager.getSavedGame(0);
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
