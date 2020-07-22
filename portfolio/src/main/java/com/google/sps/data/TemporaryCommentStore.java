package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;

public class TemporaryCommentStore implements CommentStore {

  private List<Comment> comments = new ArrayList<>();

  @Override
  public List<Comment> getComments() {
    return comments;
  }
  
  @Override
  public void post(Comment comment) {
    comments.add(comment);
  }
}