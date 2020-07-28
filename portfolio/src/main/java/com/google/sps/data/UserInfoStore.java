package com.google.sps.data;

public interface UserInfoStore {

  String getNickname(String id);

  void setNickname(String id, String nickname);

}
