package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.JsonObject;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/auth/check")
public class AuthCheckServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    UserService userService = UserServiceFactory.getUserService();

    response.setContentType("application/json");

    if (!userService.isUserLoggedIn()) {
      JsonObject userInfo = new JsonObject();
      userInfo.addProperty("loggedIn", false);
      userInfo.addProperty("loginURL", userService.createLoginURL("/"));

      response.getWriter().println(userInfo.toString());
    } else {
      String id = userService.getCurrentUser().getUserId();
      String logoutURL = userService.createLogoutURL("/");

      JsonObject userInfo = new JsonObject();
      userInfo.addProperty("loggedIn", true);
      userInfo.addProperty("id", id);
      userInfo.addProperty("logoutURL", logoutURL);

      response.getWriter().println(userInfo.toString());
    }
  }
}
