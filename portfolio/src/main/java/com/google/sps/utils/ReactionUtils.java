package com.google.sps.utils;

import com.google.sps.data.Reaction;
import java.util.HashMap;
import java.util.Map;

public class ReactionUtils {

  public final static Map<String, Reaction> REACTION_MAP = initReactionMap();

  private static Map<String, Reaction> initReactionMap() {
    Map<String, Reaction> mapping = new HashMap<>();

    mapping.put("none", Reaction.NONE);
    mapping.put("like", Reaction.LIKE);
    mapping.put("love", Reaction.LOVE);
    mapping.put("wow", Reaction.WOW);
    mapping.put("laugh", Reaction.LAUGH);

    return mapping;
  }

  public static String toPropertyName(Reaction reaction) {
    switch (reaction) {
      case LIKE:
        return "likeCount";
      case LOVE:
        return "loveCount";
      case WOW:
        return "wowCount";
      case LAUGH:
        return "laughCount";
      case NONE:
        throw new IllegalArgumentException("None reaction is not stored");
    }

    return "";
  }
}
