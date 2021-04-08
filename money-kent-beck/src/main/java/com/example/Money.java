package com.example;

abstract class Money {
  protected int amount;
  protected String currency;

  Money(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }

  abstract Money times(int multiplier);

  @Override
  public boolean equals(Object object) {
    Money money = (Money) object;
    return amount == money.amount && getClass().equals(money.getClass());
  }

  static Dollar dollar(int amount) {
    return new Dollar(amount, "USD");
  }
  
  static Franc franc(int amount) {
    return new Franc(amount, "CHF");
  }

  String currency() {
    return currency;
  }
}
