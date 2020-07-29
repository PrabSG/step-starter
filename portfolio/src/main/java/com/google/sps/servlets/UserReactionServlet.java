package com.google.sps.servlets;

import static com.google.sps.utils.ReactionUtils.REACTION_MAP;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.JsonObject;
import com.google.sps.data.Reaction;
import com.google.sps.data.ReactionDatastore;
import com.google.sps.data.ReactionStore;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/posts/user/react")
public class UserReactionServlet extends HttpServlet {

  private ReactionStore store = ReactionDatastore.getInstance();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    JsonObject responseInfo = new JsonObject();
    UserService userService = UserServiceFactory.getUserService();

    if (!userService.isUserLoggedIn()) {
      responseInfo.addProperty("loggedIn", false);
    } else {
      String postId = request.getParameter("postId");

      Reaction reaction = store.getUserReaction(postId, userService.getCurrentUser().getUserId());

      responseInfo = new JsonObject();
      responseInfo.addProperty("loggedIn", true);
      responseInfo.addProperty("reaction", reaction.toString().toLowerCase());
    }

    response.setContentType("application/json");
    response.getWriter().println(responseInfo.toString());
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    UserService userService = UserServiceFactory.getUserService();

    if (userService.isUserLoggedIn()) {
      String postId = request.getParameter("postId");
      String toIncrement = request.getParameter("newReact");

      store.updatePost(postId, userService.getCurrentUser().getUserId(), REACTION_MAP.get(toIncrement));

      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }
}
