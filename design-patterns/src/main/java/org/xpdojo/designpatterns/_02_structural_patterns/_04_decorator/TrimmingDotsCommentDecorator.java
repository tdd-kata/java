package org.xpdojo.designpatterns._02_structural_patterns._04_decorator;

public class TrimmingDotsCommentDecorator extends CommentDecorator {

    public TrimmingDotsCommentDecorator(CommentService commentService) {
        super(commentService);
    }

    @Override
    public String addComment(String comment) {
        return super.addComment(trim(comment));
    }

    private String trim(String comment) {
        return comment.replace("...", "");
    }
}
