package com.markruler.plugin;

import com.markruler.util.StringUtils;

/**
 * 애플리케이션
 */
public class App {

    /**
     * 문자열을 연결해서 출력한다.
     *
     * @param args 전달 인자
     */
    public static void main(String[] args) {
        System.out.println(StringUtils.concatenate("Hello", " ", "World!"));
    }
}
