package com.google.sps.data;

import java.util.List;

public interface CommentStore {
    List<Comment> getComments();
    void post(Comment comment);
}