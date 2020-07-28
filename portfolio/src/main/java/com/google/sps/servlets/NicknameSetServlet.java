package com.google.sps.servlets;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.data.UserInfoDatastore;
import com.google.sps.data.UserInfoStore;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/nickname/set")
public class NicknameSetServlet extends HttpServlet {

  UserInfoStore store = UserInfoDatastore.getInstance();

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    UserService userService = UserServiceFactory.getUserService();

    if (!userService.isUserLoggedIn()) {
      response.sendRedirect(userService.createLoginURL(request.getHeader("referer")));
      return;
    }

    String nickname = request.getParameter("nickname").trim();

    if (nickname.equals("")) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }

    User currentUser = userService.getCurrentUser();
    store.setNickname(currentUser.getUserId(), nickname);

    response.sendRedirect("/");
  }
}
