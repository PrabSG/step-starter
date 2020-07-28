package com.google.sps.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class UserInfoDatastore implements UserInfoStore {

  private static UserInfoStore instance = new UserInfoDatastore();

  private DatastoreService datastore;

  private UserInfoDatastore() {
    this.datastore = DatastoreServiceFactory.getDatastoreService();
  }

  public static UserInfoStore getInstance() {
    return instance;
  }

  @Override
  public String getNickname(String id) {
    Key key = KeyFactory.createKey("UserInfo", id);

    try {
      Entity userEntity = datastore.get(key);
      return (String) userEntity.getProperty("nickname");
    } catch (EntityNotFoundException e) {
      return "";
    }
  }

  @Override
  public void setNickname(String id, String nickname) {
    Key userKey = KeyFactory.createKey("UserInfo", id);
    Entity user;

    try {
      user = datastore.get(userKey);
      user.setProperty("nickname", nickname);
    } catch (EntityNotFoundException e) {
      user = new Entity("UserInfo", id);
      user.setProperty("nickname", nickname);
    }

    datastore.put(user);
  }
}
