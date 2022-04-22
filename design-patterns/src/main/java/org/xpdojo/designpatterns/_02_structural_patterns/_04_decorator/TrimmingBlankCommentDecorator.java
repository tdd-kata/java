package org.xpdojo.designpatterns._02_structural_patterns._04_decorator;

public class TrimmingBlankCommentDecorator extends CommentDecorator {

    public TrimmingBlankCommentDecorator(CommentService commentService) {
        super(commentService);
    }

    @Override
    public String addComment(String comment) {
        return super.addComment(trim(comment));
    }

    private String trim(String comment) {
        return comment.replace(" ", "");
    }
}
