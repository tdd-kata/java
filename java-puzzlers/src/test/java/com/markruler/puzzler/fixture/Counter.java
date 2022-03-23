package com.markruler.puzzler.fixture;

public class Counter {
    private static int count;

    public static void increment() {
        count++;
    }

    public static int getCount() {
        return count;
    }
}
