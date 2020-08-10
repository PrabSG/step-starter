package com.google.sps.data;

public interface ReactionStore {

  Post getReactions(String postId);

  void updatePost(String postId, String userId, Reaction newReact);

  Reaction getUserReaction(String postId, String userId);
}
