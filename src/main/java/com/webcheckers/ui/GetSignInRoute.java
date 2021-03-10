
package com.webcheckers.ui;

import spark.*;

import java.util.HashMap;
import java.util.Map;

/**
 * The UI Controller to GET the Sign In page.
 */
public class GetSignInRoute implements Route {

    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetSignInRoute(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    /**
     * Render the WebCheckers Sign In page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Sign In page
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Map<String, Object> viewModel = new HashMap<>();
        viewModel.put("title", "Sign In");
        return templateEngine.render(new ModelAndView(viewModel, "signin.ftl"));
    }
}
