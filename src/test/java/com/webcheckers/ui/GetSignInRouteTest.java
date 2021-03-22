package com.webcheckers.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;


import static org.mockito.Mockito.*;

/**
 * Code coverage for GetSignInRoute
 */
@Tag("UI-tier")
public class GetSignInRouteTest {
    private GetSignInRoute CuT; // Component under test
    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine templateEngine;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        templateEngine = mock(TemplateEngine.class);
        CuT = new GetSignInRoute(templateEngine);
    }

    @Test
    public void mainTest() throws Exception {
        CuT.handle(request, response);
    }
}
