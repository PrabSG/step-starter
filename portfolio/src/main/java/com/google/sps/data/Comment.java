package com.google.sps.data;

import java.time.Instant;

public class Comment {
  private final String comment;
  private final String userId;
  private final Instant timestamp;
  private String name;
  private long id;

  public Comment(String comment, String userId, Instant timestamp) {
    this.comment = comment;
    this.userId = userId;
    this.timestamp = timestamp;
  }

  public Comment(String comment, String userId, Instant timestamp, long id) {
    this(comment, userId, timestamp);
    this.id = id;
  }

  public Comment (String comment, String userId) {
    this(comment, userId, Instant.now());
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUserId() {
    return this.userId;
  }
}