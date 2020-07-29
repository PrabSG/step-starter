package com.google.sps.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommentDatastore implements CommentStore {

  private static CommentStore instance = new CommentDatastore();

  private DatastoreService datastore;

  private CommentDatastore() {
    this.datastore = DatastoreServiceFactory.getDatastoreService();
  }

  public static CommentStore getInstance() {
    return instance;
  }

  @Override
  public List<Comment> getComments() {
    Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    List<Comment> comments = new ArrayList<>();

    for (Entity entity : results.asIterable()) {
      Comment comment = new Comment(
          (String) entity.getProperty("comment"),
          (String) entity.getProperty("userId"),
          Instant.ofEpochMilli((long) entity.getProperty("timestamp")),
          entity.getKey().getId());

      comments.add(comment);
    }

    return comments;
  }

  @Override
  public List<Comment> getComments(int limit) {
    return getComments().stream().limit(limit).collect(Collectors.toList());
  }

  @Override
  public void deleteAllComments() {
    Query query = new Query("Comment");
    PreparedQuery results = datastore.prepare(query);

    List<Entity> entities = results.asList(FetchOptions.Builder.withDefaults());
    Key[] keys = entities.stream().map(Entity::getKey).toArray(Key[]::new);

    datastore.delete(keys);
  }

  @Override
  public void post(String comment, String userId) {
    Entity commentEntity = new Entity("Comment");
    commentEntity.setProperty("comment", comment);
    commentEntity.setProperty("userId", userId);
    commentEntity.setProperty("timestamp", Instant.now().toEpochMilli());

    datastore.put(commentEntity);
  }
}