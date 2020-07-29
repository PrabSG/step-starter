package com.google.sps.servlets;

import com.google.gson.Gson;
import com.google.sps.data.Post;
import com.google.sps.data.ReactionDatastore;
import com.google.sps.data.ReactionStore;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("posts/reactions")
public class PostReactionsServlet extends HttpServlet {

  private ReactionStore store = ReactionDatastore.getInstance();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String postId = request.getParameter("postId");

    Post post = store.getReactions(postId);

    Gson gson = new Gson();

    response.setContentType("application/json");
    response.getWriter().println(gson.toJson(post));
  }
}
