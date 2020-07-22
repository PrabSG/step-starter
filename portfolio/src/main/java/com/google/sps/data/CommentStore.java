package com.google.sps.data;

import java.util.List;

public interface CommentStore {
    List<Comment> getComments();
    List<Comment> getComments(int limit);
    void post(Comment comment);
}