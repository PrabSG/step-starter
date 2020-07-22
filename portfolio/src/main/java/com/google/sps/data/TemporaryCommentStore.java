package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TemporaryCommentStore implements CommentStore {

  private List<Comment> comments = new ArrayList<>();

  @Override
  public List<Comment> getComments() {
    return comments;
  }

  @Override
  public List<Comment> getComments(int limit) {
    return getComments().stream().limit(limit).collect(Collectors.toList());
  }

  @Override
  public void post(Comment comment) {
    comments.add(comment);
  }
}