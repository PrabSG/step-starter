package com.google.sps.servlets;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.JsonObject;
import com.google.sps.data.UserInfoDatastore;
import com.google.sps.data.UserInfoStore;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/nickname/check")
public class NicknameCheckServlet extends HttpServlet {

  UserInfoStore store = UserInfoDatastore.getInstance();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    UserService userService = UserServiceFactory.getUserService();

    if (!userService.isUserLoggedIn()) {
      response.sendRedirect(userService.createLoginURL(request.getHeader("referer")));
    }

    User currentUser = userService.getCurrentUser();
    String nickname = store.getNickname(currentUser.getUserId());

    response.setContentType("application/json");

    JsonObject responseInfo = new JsonObject();
    responseInfo.addProperty("hasNickname", !nickname.equals(""));

    response.getWriter().println(responseInfo.toString());
  }
}
