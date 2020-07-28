// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.sps.data.Comment;
import com.google.sps.data.CommentDatastore;
import com.google.sps.data.CommentStore;

/**
 * Servlet that returns comments posted onto the portfolio page.
 */
@WebServlet("/comments")
public class CommentsServlet extends HttpServlet {

  public static final int DEFAULT_COMMENT_LIMIT = 15;

  private CommentStore store = CommentDatastore.getInstance();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserService userService = UserServiceFactory.getUserService();

    if (!userService.isUserLoggedIn()) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    String limitString = getParameter(request, "limit", String.valueOf(DEFAULT_COMMENT_LIMIT));
    int limit;

    try {
      limit = Integer.parseInt(limitString);
    } catch (NumberFormatException e) {
      limit = DEFAULT_COMMENT_LIMIT;
    }

    response.setContentType("application/json;");

    Gson gson = new Gson();
    response.getWriter().println(gson.toJson(store.getComments(limit)));
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String userName = getParameter(request, "name", "Anonymous");
    String comment = getParameter(request, "comment", "");

    UserService userService = UserServiceFactory.getUserService();

    if (userService.isUserLoggedIn()) {
      store.post(new Comment(userName, comment));
    }


    response.sendRedirect("./index.html");
  }

  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);

    return (value != null) ? value : defaultValue;
  }
}
