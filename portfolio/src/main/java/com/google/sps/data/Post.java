package com.google.sps.data;

import java.util.Map;

public class Post {

  private final String postId;
  private final Map<Reaction, Integer> reactCounts;

  public Post(String postId, Map<Reaction, Integer> reactCounts) {
    this.postId = postId;
    this.reactCounts = reactCounts;
  }
}