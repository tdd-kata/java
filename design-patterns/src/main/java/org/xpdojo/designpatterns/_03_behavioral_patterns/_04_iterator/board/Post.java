package org.xpdojo.designpatterns._03_behavioral_patterns._04_iterator.board;

import lombok.ToString;

import java.time.LocalDateTime;

@ToString
public class Post {

    private String title;

    private LocalDateTime createdDateTime;

    public Post(String title) {
        this.title = title;
        this.createdDateTime = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }
}
