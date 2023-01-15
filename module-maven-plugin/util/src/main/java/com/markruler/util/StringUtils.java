package com.markruler.util;

import com.markruler.error.StringConcatException;

/**
 * 문자열 유틸리티 클래스
 *
 * @see <a href="https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/StringUtils.html">Apache Commons Lang</a>
 */
public class StringUtils {

    private StringUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 문자열을 연결합니다.
     *
     * @param strings 연결할 문자열
     * @return 연결된 문자열
     */
    public static String concatenate(String... strings) {
        if (strings.length < 2) {
            throw new StringConcatException("문자열은 두 개 이상이어야 합니다.");
        }
        return String.join("", strings);
    }
}
