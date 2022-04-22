package org.xpdojo.designpatterns._02_structural_patterns._04_decorator;

public class Client {

    private CommentService commentService;

    public Client(CommentService commentService) {
        this.commentService = commentService;
    }

    public String writeComment(String comment) {
        return commentService.addComment(comment);
    }
}
