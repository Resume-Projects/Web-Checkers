package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.mockito.Mockito.*;

@Tag("UI-Tier")
public class PostSignInRouteTest {

    private PostSignInRoute CuT;
    private Request request;
    private Response response;
    private Session session;
    private PlayerLobby playerlobby;
    private TemplateEngine templateEngine;


    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        playerlobby = mock(PlayerLobby.class);
        when(request.session()).thenReturn(session);
        templateEngine = mock(TemplateEngine.class);
        CuT = new PostSignInRoute(playerlobby, templateEngine);
    }

    @Test
    public void signIn() throws Exception {
        String name = "player";
        when(request.queryParams("username")).thenReturn(name);
        when(playerlobby.isNameTaken(name)).thenReturn(false);
        when(playerlobby.isValidName(name)).thenReturn(true);
        CuT.handle(request, response);
        verify(response, times(1)).redirect("/");
    }

    @Test
    public void nameTaken() throws Exception {
        Player taken = mock(Player.class);
        String name = taken.getName();
        when(request.queryParams("username")).thenReturn(name);
        when(playerlobby.isNameTaken(name)).thenReturn(true);
        CuT.handle(request, response);
        verify(playerlobby, never()).newPlayer(name);
    }

    @Test
    public void invalidName() throws Exception {
        String name = "$$$$";
        when(request.queryParams("username")).thenReturn(name);
        when(playerlobby.isValidName(name)).thenReturn(false);
        CuT.handle(request, response);
        verify(playerlobby, never()).newPlayer(name);
    }

    @Test
    public void handle() throws Exception{
        CuT.handle(request, response);
    }
}
