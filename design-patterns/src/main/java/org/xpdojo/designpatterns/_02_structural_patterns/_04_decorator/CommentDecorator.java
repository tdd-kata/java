package org.xpdojo.designpatterns._02_structural_patterns._04_decorator;

/**
 * Decorator
 */
public class CommentDecorator implements CommentService {

    private CommentService commentService;

    public CommentDecorator(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public String addComment(String comment) {
        return commentService.addComment(comment);
    }
}
