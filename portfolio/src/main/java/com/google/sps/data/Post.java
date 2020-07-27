package com.google.sps.data;

import java.util.HashMap;
import java.util.Map;

public class Post {

  private final String postId;
  private Map<String, Long> reactCounts;

  public Post(String postId) {
    this.postId = postId;
    this.reactCounts = new HashMap<>();
    this.reactCounts.put("likeCount", 0L);
    this.reactCounts.put("loveCount", 0L);
    this.reactCounts.put("wowCount", 0L);
    this.reactCounts.put("laughCount", 0L);
  }

  public String getPostId() {
    return postId;
  }

  public Map<String, Long> getReactCounts() {
    return reactCounts;
  }

  public void setReactCounts(Map<String, Long> reactCounts) {
    this.reactCounts = reactCounts;
  }
}