package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.application.GameManager;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Player;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final GameManager gameManager;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetHomeRoute(final GameManager gameManager, final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        this.playerLobby = playerLobby;
        this.gameManager = gameManager;
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("GetHomeRoute is initialized.");
    }

    /**
     * Render the WebCheckers Home page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetHomeRoute is invoked.");

        //A game has been created
        Player currentUser = request.session().attribute("currentUser");

        //If the player is in a game, show them the game
        if(currentUser != null) {
            CheckersGame playersGame = gameManager.getPlayersGame(currentUser);
            if(playersGame != null) { //If the player was never involved with a game, nothing should happen
                if(!playersGame.isGameDone()) {
                    response.redirect("/game");
                    return null; //They get sent to the game page
                } else {
                    if(playersGame.hasPlayerLeft()) {
                        gameManager.deleteGame(currentUser);
                    } else {
                        playersGame.removePlayer(currentUser);
                    }
                }
            }
        }

        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Welcome!");

        // display a user message in the Home page
        if(request.session().attribute("errorMessage") == null)
            vm.put("message", WELCOME_MSG);
        else
            vm.put("message", Message.error(request.session().attribute("errorMessage")));

        vm.put("numPlayers", playerLobby.getActivePlayers().size());
        vm.put("numGames", gameManager.getActiveGames().size());

        if(currentUser != null) {
            vm.put("currentUser", currentUser);
            vm.put("activePlayers", playerLobby.getActivePlayers().values());
            vm.put("activeGames", gameManager.getActiveGames().values());
        }

        // render the View
        return templateEngine.render(new ModelAndView(vm , "home.ftl"));
    }
}
