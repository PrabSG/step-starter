package com.google.sps.data;

public class UserInfo {

  private boolean loggedIn;
  private String id;
  private String nickname;

  public UserInfo() {
    this.loggedIn = false;
  }

  public UserInfo(boolean loggedIn, String id) {
    this.loggedIn = loggedIn;
    this.id = id;
  }

  public boolean isLoggedIn() {
    return loggedIn;
  }

  public void setLoggedIn(boolean loggedIn) {
    this.loggedIn = loggedIn;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
