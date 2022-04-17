package org.xpdojo.jdbc.domain;

import lombok.Builder;

public class Member {

    private String id;
    private int money;

    public Member() {
    }

    @Builder
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

    /**
     * 동등성 비교
     *
     * @param o 비교할 객체
     * @return 동등성 비교 결과
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member member = (Member) o;

        if (money != member.money) return false;
        return id.equals(member.id);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + money;
        return result;
    }
}
