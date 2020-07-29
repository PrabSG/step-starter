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
}
