package org.xpdojo.designpatterns._02_structural_patterns._04_decorator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DecoratorTest {

    @Test
    void sut_descorator() {
        CommentService commentService = new DefaultCommentService();
        commentService = new TrimmingBlankCommentDecorator(new TrimmingDotsCommentDecorator(commentService));
        commentService = new SpamFilteringCommentDecorator(commentService);

        Client client = new Client(commentService);
        assertThat(client.writeComment("   오징어 게임...   ")).isEqualTo("오징어게임");
        assertThat(client.writeComment("http://xpdojo.org")).isEmpty();
    }
}

