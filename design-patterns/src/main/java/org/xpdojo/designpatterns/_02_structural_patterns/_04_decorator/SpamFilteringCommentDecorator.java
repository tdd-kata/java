package org.xpdojo.designpatterns._02_structural_patterns._04_decorator;

public class SpamFilteringCommentDecorator extends CommentDecorator {

    public SpamFilteringCommentDecorator(CommentService commentService) {
        super(commentService);
    }

    @Override
    public String addComment(String comment) {
        if (isNotSpam(comment)) {
            return super.addComment(comment);
        }
        return "";
    }

    private boolean isNotSpam(String comment) {
        return !comment.contains("http");
    }
}
