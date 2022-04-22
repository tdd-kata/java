package org.xpdojo.designpatterns._02_structural_patterns._04_decorator;

public class DefaultCommentService implements CommentService {
    @Override
    public String addComment(String comment) {
        return comment;
    }
}
