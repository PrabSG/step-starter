package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderCommentStore implements CommentStore {
  private List<String> comments;

  public PlaceholderCommentStore() {
    comments = new ArrayList();
    comments.add("Hello this is the first comment.");
    comments.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus elementum " +
        "condimentum diam vel feugiat. Integer porta iaculis tortor nec viverra.");
    comments.add("Nulla ut odio quis neque lacinia sollicitudin ut non nunc. Donec lobortis " +
        "fermentum quam, id blandit sapien commodo ut. Duis in felis vel lorem lobortis ornare " +
        "non quis nunc.");
  }

  @Override
  public List<String> getComments() {
    return comments;
  }

  @Override
  public boolean post(Comment comment) {
    return true;
  }
}