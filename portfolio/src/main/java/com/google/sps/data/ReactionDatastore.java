package com.google.sps.data;

import static com.google.sps.data.Reaction.LAUGH;
import static com.google.sps.data.Reaction.LIKE;
import static com.google.sps.data.Reaction.LOVE;
import static com.google.sps.data.Reaction.NONE;
import static com.google.sps.data.Reaction.WOW;
import static com.google.sps.utils.ReactionUtils.toPropertyName;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
import java.util.HashMap;
import java.util.Map;

public class ReactionDatastore {

  private static ReactionDatastore instance = new ReactionDatastore();

  private DatastoreService datastore;

  private ReactionDatastore() {
    this.datastore = DatastoreServiceFactory.getDatastoreService();
  }

  public static ReactionDatastore getInstance() {
    return instance;
  }

  public Post getReactions(String postId) {
    try {
      Entity postEntity = datastore.get(KeyFactory.createKey("Post", postId));

      Post post = new Post(postId);
      post.setLikeCount((Long) postEntity.getProperty("likeCount"));
      post.setLoveCount((Long) postEntity.getProperty("loveCount"));
      post.setWowCount((Long) postEntity.getProperty("wowCount"));
      post.setLaughCount((Long) postEntity.getProperty("laughCount"));

      return post;
    } catch (EntityNotFoundException e) {
      Entity postEntity = createNewPost(postId);

      datastore.put(postEntity);

      return new Post(postId);
    }
  }

  public void updatePost(String postId, Reaction toDecrement, Reaction toIncrement) {
    Transaction txn = datastore.beginTransaction();

    try {
      Key postKey = KeyFactory.createKey("Post", postId);
      Entity postEntity = datastore.get(postKey);

      if (toDecrement != NONE) {
        postEntity.setProperty(toPropertyName(toDecrement),
            (long) postEntity.getProperty(toPropertyName(toDecrement)) - 1);
      }

      if (toIncrement != NONE) {
        postEntity.setProperty(toPropertyName(toIncrement),
            (long) postEntity.getProperty(toPropertyName(toIncrement)) + 1);
      }

      datastore.put(txn, postEntity);

      txn.commit();
    } catch (EntityNotFoundException e) {
      Entity postEntity = createNewPost(postId);

      if (toIncrement != NONE) {
        postEntity.setProperty(toPropertyName(toIncrement), 1);
      }

      datastore.put(txn, postEntity);

      txn.commit();
    } finally {
      if (txn.isActive()) {
        txn.rollback();
      }
    }
  }

  private Entity createNewPost(String postId) {
    Entity postEntity = new Entity("Post", postId);

    postEntity.setProperty("likeCount", 0);
    postEntity.setProperty("loveCount", 0);
    postEntity.setProperty("wowCount", 0);
    postEntity.setProperty("laughCount", 0);

    return postEntity;
  }
}
