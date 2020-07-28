package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.sps.data.UserInfo;
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
      StringBuilder loggedOutResp = new StringBuilder();
      loggedOutResp.append("{");
      loggedOutResp.append("\"loggedIn\": false,");
      loggedOutResp.append("\"loginURL\":");
      loggedOutResp.append("\"");
      loggedOutResp.append(userService.createLoginURL("/"));
      loggedOutResp.append("\"");
      loggedOutResp.append("}");

      response.getWriter().println(loggedOutResp.toString());
    } else {
      String id = userService.getCurrentUser().getUserId();
      String logoutURL = userService.createLogoutURL("/");

      UserInfo userInfo = new UserInfo(true, id);
      Gson gson = new Gson();

      JsonElement responseInfo = gson.toJsonTree(userInfo);
      responseInfo.getAsJsonObject().addProperty("logoutURL", logoutURL);

      response.getWriter().println(gson.toJson(responseInfo));
    }
  }
}
