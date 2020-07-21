package com.google.sps.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

import java.util.ArrayList;
import java.util.List;

public class CommentDatastore implements CommentStore {

  private static CommentDatastore instance = new CommentDatastore();

  private DatastoreService datastore;

  private CommentDatastore() {
    this.datastore = DatastoreServiceFactory.getDatastoreService();
  }

  public static CommentDatastore getInstance() {
    return instance;
  }

  @Override
  public List<Comment> getComments() {
    Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    List<Comment> comments = new ArrayList<>();

    for (Entity entity : results.asIterable()) {
      Comment comment = new Comment((String) entity.getProperty("name"),
          (String) entity.getProperty("comment"),
          (long) entity.getProperty("timestamp"));

      comments.add(comment);
    }

    return comments;
  }

  @Override
  public void post(Comment comment) {
    Entity commentEntity = new Entity("Comment");
    commentEntity.setProperty("name", comment.getName());
    commentEntity.setProperty("comment", comment.getComment());
    commentEntity.setProperty("timestamp", comment.getTimestamp());

    datastore.put(commentEntity);
  }
}