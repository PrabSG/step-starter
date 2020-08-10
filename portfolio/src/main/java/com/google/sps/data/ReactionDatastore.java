package com.google.sps.data;

import static com.google.sps.data.Reaction.NONE;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions.Builder;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Transaction;
import java.util.HashMap;
import java.util.Map;

public class ReactionDatastore implements ReactionStore {

  private static ReactionDatastore instance = new ReactionDatastore();

  private DatastoreService datastore;

  private ReactionDatastore() {
    this.datastore = DatastoreServiceFactory.getDatastoreService();
  }

  public static ReactionDatastore getInstance() {
    return instance;
  }

  @Override
  public Post getReactions(String postId) {
    Filter postFilter = new FilterPredicate("postId", FilterOperator.EQUAL, postId);
    Map<Reaction, Integer> reactCounts = new HashMap<>();

    for (Reaction reaction : Reaction.values()) {
      Filter reactFilter = new FilterPredicate("reaction", FilterOperator.EQUAL,
          reaction.toString());
      Query query = new Query("UserReaction")
          .setFilter(CompositeFilterOperator.and(postFilter, reactFilter));

      reactCounts.put(reaction, getQueryCount(query));
    }

    return new Post(postId, reactCounts);
  }

  private int getQueryCount(Query query) {
    return datastore.prepare(query).countEntities(Builder.withDefaults());
  }

  @Override
  public void updatePost(String postId, String userId, Reaction newReact) {
    Transaction txn = datastore.beginTransaction();

    try {
      Filter matchingPost = new FilterPredicate("postId", FilterOperator.EQUAL, postId);
      Filter matchingUser = new FilterPredicate("userId", FilterOperator.EQUAL, userId);
      Filter combinedFilter = CompositeFilterOperator.and(matchingPost, matchingUser);

      Query query = new Query("UserReaction").setFilter(combinedFilter);

      PreparedQuery results = datastore.prepare(query);
      Entity reaction = results.asSingleEntity();

      if (newReact != NONE) {
        if (reaction == null) {
          reaction = new Entity("UserReaction");
          reaction.setProperty("postId", postId);
          reaction.setProperty("userId", userId);
        }

        reaction.setProperty("reaction", newReact.toString());

        datastore.put(txn, reaction);
      } else {
        if (reaction != null) {
          datastore.delete(reaction.getKey());
        }
      }

      txn.commit();
    } finally {
      if (txn.isActive()) {
        txn.rollback();
      }
    }
  }

  @Override
  public Reaction getUserReaction(String postId, String userId) {
    Filter postFilter = new FilterPredicate("postId", FilterOperator.EQUAL, postId);
    Filter userFilter = new FilterPredicate("userId", FilterOperator.EQUAL, userId);
    Filter combinedFilter = CompositeFilterOperator.and(postFilter, userFilter);

    Query query = new Query("UserReaction").setFilter(combinedFilter);

    Entity reaction = datastore.prepare(query).asSingleEntity();

    if (reaction == null) {
      return NONE;
    } else {
      return Reaction.valueOf((String) reaction.getProperty("reaction"));
    }
  }
}
