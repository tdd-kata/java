package org.xpdojo.sof;

/**
 * 상호 참조 클래스
 */
public class ClassTwo {
    private ClassOne classOne;

    public ClassTwo() {
        classOne = new ClassOne();
    }
}
