
package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;

public class PostSignInRoute implements Route {

    private PlayerLobby playerLobby;
    private TemplateEngine templateEngine;

    public PostSignInRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String playerName = request.queryParams("username");
        if(playerLobby.isNameTaken(playerName)) {

        } else if(!playerLobby.isValidName(playerName)) {

        } else {
            System.out.println("HELLO");
            Player clientPlayer = playerLobby.newPlayer(playerName);
            request.session().attribute("clientPlayer", clientPlayer);
            response.redirect("/");
        }
        Map<String, Object> viewModel = new HashMap<>();
        return templateEngine.render(new ModelAndView(viewModel, "signin.ftl"));
    }
}
