package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlaceholderCommentStore implements CommentStore {
  private List<Comment> comments;

  public PlaceholderCommentStore() {
    comments = new ArrayList();
    comments.add(new Comment("Hello this is the first comment."));
    comments.add(
      new Comment("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus elementum " +
        "condimentum diam vel feugiat. Integer porta iaculis tortor nec viverra.")
    );
    comments.add(
      new Comment("Nulla ut odio quis neque lacinia sollicitudin ut non nunc. Donec lobortis " +
        "fermentum quam, id blandit sapien commodo ut. Duis in felis vel lorem lobortis ornare " +
        "non quis nunc.")
    );
  }

  @Override
  public List<Comment> getComments() {
    return comments;
  }

  @Override
  public List<Comment> getComments(int limit) {
    return comments.stream().limit(limit).collect(Collectors.toList());
  }

  @Override
  public void post(Comment comment) {
    return;
  }
}