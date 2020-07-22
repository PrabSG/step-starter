package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TemporaryCommentStore implements CommentStore {

  private List<Comment> comments = new ArrayList<>();

  @Override
  public List<String> getComments() {
    return comments.stream()
        .map(c -> c.getComment() + "\nPosted by " + c.getName())
        .collect(Collectors.toList());
  }
  
  @Override
  public boolean post(Comment comment) {
    return comments.add(comment);
  }
}