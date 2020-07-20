package com.google.sps.data;

import java.util.List;

public interface CommentStore {
    List<String> getComments();
    boolean post();
}