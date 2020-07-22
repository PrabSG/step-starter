package com.google.sps.data;

public class Comment {
  private final String name;
  private final String comment;
  private final long timestamp;

  public Comment(String name, String comment, long timestamp) {
    this.name = name;
    this.comment = comment;
    this.timestamp = timestamp;
  }

  public Comment (String name, String comment) {
    this(name, comment, System.currentTimeMillis());
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

  public long getTimestamp() {
    return this.timestamp;
  }
}