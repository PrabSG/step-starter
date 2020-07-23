package com.google.sps.data;

import java.time.Instant;

public class Comment {
  private final String name;
  private final String comment;
  private final Instant timestamp;

  public Comment(String name, String comment, Instant timestamp) {
    this.name = name;
    this.comment = comment;
    this.timestamp = timestamp;
  }

  public Comment (String name, String comment) {
    this(name, comment, Instant.now());
  }

  public Comment(String comment) {
    this("", comment);
  }

  public Comment() {
    this("", "");
  }

  public String getName() {
    return this.name;
  }

  public String getComment() {
    return this.comment;
  }

  public Instant getTimestamp() {
    return this.timestamp;
  }
}