package org.xpdojo.designpatterns._03_behavioral_patterns._05_mediator;

import org.xpdojo.designpatterns._03_behavioral_patterns._05_mediator.hotel.FrontDesk;

import java.time.LocalDateTime;

public class Guest {

    private final Integer id;

    private final FrontDesk frontDesk = new FrontDesk();

    public Guest(Integer id) {
        this.id = id;
    }

    public String getTowers(int numberOfTowers) {
        return this.frontDesk.getTowers(this, numberOfTowers);
    }

    private void dinner(LocalDateTime dateTime) {
        this.frontDesk.dinner(this, dateTime);
    }

    public Integer getId() {
        return id;
    }
}
