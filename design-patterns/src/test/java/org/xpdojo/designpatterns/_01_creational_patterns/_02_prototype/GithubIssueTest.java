package org.xpdojo.designpatterns._01_creational_patterns._02_prototype;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GithubIssueTest {

    @Test
    @DisplayName("프로토타입 패턴으로 생성하면 객체가 다르다")
    void testClone() throws CloneNotSupportedException {

        GithubRepository repository = new GithubRepository();
        repository.setUser("markruler");
        repository.setName("xpdojo");

        GithubIssue githubIssue = new GithubIssue(repository);
        githubIssue.setId(1);
        githubIssue.setTitle("GoF Design Patterns");

        GithubIssue clone = (GithubIssue) githubIssue.clone();

        repository.setUser("imcxsu");

        assertThat(clone)
                .isNotSameAs(githubIssue)
                .isNotEqualTo(githubIssue);

        assertThat(clone.getClass()).isSameAs(githubIssue.getClass());
        assertThat(clone.getRepository()).isNotSameAs(githubIssue.getRepository());
    }
}
