package org.xpdojo.springbootvue.domain;

import lombok.Getter;

@Getter
public class Player {
    private Long id;
    private String name;
    private String description;

    public Player(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
