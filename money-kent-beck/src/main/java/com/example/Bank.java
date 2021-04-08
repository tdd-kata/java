package com.example;

import java.util.Hashtable;

class Bank {
  private Hashtable<Object, Object> rates = new Hashtable<>();

  Money reduce(Expression source, String to) {
    return source.reduce(this, to);
  }

  int rate(String from, String to) {
    if (from.equals(to)) return 1;
    Integer rate = (Integer) rates.get(new Pair(from, to));
    return rate.intValue();
  }

  void addRate(String from, String to, int rate) {
    rates.put(new Pair(from, to), Integer.valueOf(rate));
  }
}
