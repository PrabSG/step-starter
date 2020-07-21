package com.google.sps.data;

public class Comment {
  private final String name;
  private final String comment;

  public Comment (String name, String comment) {
    this.name = name;
    this.comment = comment;
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
}