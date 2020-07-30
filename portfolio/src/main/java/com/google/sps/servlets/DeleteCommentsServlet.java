package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.data.CommentDatastore;
import com.google.sps.data.CommentStore;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet that allows for the deletion of comments on the portfolio page.
 */
@WebServlet("/delete-comments")
public class DeleteCommentsServlet extends HttpServlet {

  private CommentStore store = CommentDatastore.getInstance();

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserService userService = UserServiceFactory.getUserService();

    if (!userService.isUserLoggedIn()) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    store.deleteAllComments();

    response.setStatus(HttpServletResponse.SC_OK);
  }
}
