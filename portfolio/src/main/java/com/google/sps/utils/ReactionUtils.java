package com.google.sps.utils;

import com.google.sps.data.Reaction;

public class ReactionUtils {

  public static Reaction toReaction(String reactStr) {
    if (reactStr == null) {
      return Reaction.NONE;
    }
    switch (reactStr) {
      case "like":
        return Reaction.LIKE;
      case "love":
        return Reaction.LOVE;
      case "wow":
        return Reaction.WOW;
      case "laugh":
        return Reaction.LAUGH;
      default:
        return Reaction.NONE;
    }
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
