package com.example;

interface Expression {
  Money reduce(Bank bank, String to); // reduced: 축약된
  Expression plus(Expression addend);
  Expression times(int multiplier);
}
