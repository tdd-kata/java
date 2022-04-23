package org.xpdojo.designpatterns._03_behavioral_patterns._07_observer.spring;

import org.springframework.context.ApplicationEvent;

public class MyEvent {

    private String message;

    public MyEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
