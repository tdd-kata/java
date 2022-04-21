package org.xpdojo.designpatterns._02_structural_patterns._01_adapter.security.adaptee;

/**
 * 추가 기능을 사용하고 싶은 Adaptee 클래스
 */
public class AccountService {

    public Account findAccountByUsername(String username) {
        Account account = new Account();
        account.setName(username);
        account.setPassword("password");
        account.setEmail(username);
        return account;
    }

    public void createNewAccount(Account account) {

    }

    public void updateAccount(Account account) {

    }

}
