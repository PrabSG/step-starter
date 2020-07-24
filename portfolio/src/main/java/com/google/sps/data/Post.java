package com.google.sps.data;

public class Post {

  private final String postId;
  private long likeCount;
  private long loveCount;
  private long wowCount;
  private long laughCount;

  public Post(String postId) {
    this.postId = postId;
    this.likeCount = 0;
    this.loveCount = 0;
    this.wowCount = 0;
    this.laughCount = 0;
  }

  public String getPostId() {
    return postId;
  }

  public long getLikeCount() {
    return likeCount;
  }

  public void setLikeCount(long likeCount) {
    this.likeCount = likeCount;
  }

  public long getLoveCount() {
    return loveCount;
  }

  public void setLoveCount(long loveCount) {
    this.loveCount = loveCount;
  }

  public long getWowCount() {
    return wowCount;
  }

  public void setWowCount(long wowCount) {
    this.wowCount = wowCount;
  }

  public long getLaughCount() {
    return laughCount;
  }

  public void setLaughCount(long laughCount) {
    this.laughCount = laughCount;
  }
}