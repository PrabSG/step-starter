package com.google.sps.data;

public class Comment {
  private final String name;
  private final String comment;
  /**
   * Timestamp in milliseconds according to epoch/unix time.
   * */
  private final long timestampMillis;

  public Comment(String name, String comment, long timestampMillis) {
    this.name = name;
    this.comment = comment;
    this.timestampMillis = timestampMillis;
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

  public long getTimestampMillis() {
    return this.timestampMillis;
  }
}