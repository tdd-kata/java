package org.xpdojo.designpatterns._03_behavioral_patterns._07_observer.chat;

public class User implements Subscriber {

    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void handleMessage(String message) {
        System.out.printf("[%s] %s%n", name, message);
    }
}
