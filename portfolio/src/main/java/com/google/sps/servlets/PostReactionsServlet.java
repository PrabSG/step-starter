package com.google.sps.servlets;

import static com.google.sps.utils.ReactionUtils.REACTION_MAP;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.sps.data.Post;
import com.google.sps.data.ReactionDatastore;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("posts/react")
public class PostReactionsServlet extends HttpServlet {

  private ReactionDatastore store = ReactionDatastore.getInstance();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String postId = request.getParameter("postId");

    Post post = store.getReactions(postId);

    response.setContentType("application/json");

    Gson gson = new Gson();
    response.getWriter().println(gson.toJson(post));
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    UserService userService = UserServiceFactory.getUserService();

    if (userService.isUserLoggedIn()) {
      String postId = request.getParameter("postId");
      String toDecrement = request.getParameter("oldReact");
      String toIncrement = request.getParameter("newReact");

      store.updatePost(postId, REACTION_MAP.get(toDecrement), REACTION_MAP.get(toIncrement));

      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }
}
