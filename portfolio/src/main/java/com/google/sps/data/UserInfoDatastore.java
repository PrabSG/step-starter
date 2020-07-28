package com.google.sps.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
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
    Query query = new Query("UserInfo")
          .setFilter(new Query.FilterPredicate("id", FilterOperator.EQUAL, id));
    PreparedQuery results = datastore.prepare(query);
    Entity userEntity = results.asSingleEntity();

    if (userEntity == null) {
      return "";
    }

    return (String) userEntity.getProperty("nickname");
  }

  @Override
  public void setNickname(String id, String nickname) {

  }
}
