package org.xpdojo.jdbc.domain;

public class Member {

    private String id;
    private int money;

    public Member() {
    }

    public Member(String id, int money) {
        this.id = id;
        this.money = money;
    }

    public String getId() {
        return id;
    }

    public int getMoney() {
        return money;
    }
}
