package com.google.sps.servlets;

import static com.google.sps.utils.ReactionUtils.toReaction;

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
    super.doGet(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String postId = request.getParameter("postId");
    String toDecrement = request.getParameter("oldReact");
    String toIncrement = request.getParameter("newReact");

    store.updatePost(postId, toReaction(toDecrement), toReaction(toIncrement));

    response.setStatus(HttpServletResponse.SC_OK);
  }

}
