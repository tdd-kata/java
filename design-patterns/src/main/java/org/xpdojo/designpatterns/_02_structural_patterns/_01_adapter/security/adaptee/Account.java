package org.xpdojo.designpatterns._02_structural_patterns._01_adapter.security.adaptee;

/**
 * 추가 기능을 사용하고 싶은 Adaptee 클래스
 */
public class Account {

    private String name;

    private String password;

    private String email;

    public Account() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
