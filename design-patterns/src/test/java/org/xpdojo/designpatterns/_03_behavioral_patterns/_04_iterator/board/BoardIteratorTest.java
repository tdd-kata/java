package org.xpdojo.designpatterns._03_behavioral_patterns._04_iterator.board;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BoardIteratorTest {

    @Test
    void sut_board_iterator() throws InterruptedException {

        Board board = new Board();
        board.addPost("디자인 패턴 게임");
        Thread.sleep(1);
        board.addPost("선생님, 저랑 디자인 패턴 하나 학습하시겠습니까?");
        Thread.sleep(1);
        board.addPost("지금 이 자리에 계신 여러분들은 모두 디자인 패턴을 학습하고 계신 분들입니다.");

        List<Post> posts = board.getPosts();
        Iterator<Post> iterator = posts.iterator();
        assertThat(iterator.getClass().getName()).isEqualTo("java.util.ArrayList$Itr");

        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next().getTitle()).isEqualTo("디자인 패턴 게임");
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next().getTitle()).isEqualTo("선생님, 저랑 디자인 패턴 하나 학습하시겠습니까?");
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next().getTitle()).isEqualTo("지금 이 자리에 계신 여러분들은 모두 디자인 패턴을 학습하고 계신 분들입니다.");
        assertThat(iterator.hasNext()).isFalse();

        Iterator<Post> recentPostIterator = board.getRecentPostIterator();
        assertThat(recentPostIterator.getClass().getName()).isEqualTo("org.xpdojo.designpatterns._03_behavioral_patterns._04_iterator.board.RecentPostIterator");

        assertThat(recentPostIterator.hasNext()).isTrue();
        assertThat(recentPostIterator.next().getTitle()).isEqualTo("지금 이 자리에 계신 여러분들은 모두 디자인 패턴을 학습하고 계신 분들입니다.");
        assertThat(recentPostIterator.hasNext()).isTrue();
        assertThat(recentPostIterator.next().getTitle()).isEqualTo("선생님, 저랑 디자인 패턴 하나 학습하시겠습니까?");
        assertThat(recentPostIterator.hasNext()).isTrue();
        assertThat(recentPostIterator.next().getTitle()).isEqualTo("디자인 패턴 게임");
        assertThat(recentPostIterator.hasNext()).isFalse();
    }

}
