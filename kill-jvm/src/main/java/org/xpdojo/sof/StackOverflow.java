package org.xpdojo.sof;

/**
 * 재귀 함수
 *
 * <pre>
 *     javac -Xlint:unchecked StackOverflow.java
 *     java -Xss256k StackOverflow
 * </pre>
 *
 * <pre>
 *     Depth of stack:26108
 * </pre>
 *
 * <pre>
 *     Exception in thread "main" java.lang.StackOverflowError
 *         at StackOverflow.doSomething(StackOverflow.java:26)
 *         at StackOverflow.doSomething(StackOverflow.java:26)
 *         at StackOverflow.doSomething(StackOverflow.java:26)
 *         at StackOverflow.doSomething(StackOverflow.java:26)
 *         at StackOverflow.doSomething(StackOverflow.java:26)
 *         at StackOverflow.doSomething(StackOverflow.java:26)
 *         at StackOverflow.doSomething(StackOverflow.java:26)
 *         at StackOverflow.doSomething(StackOverflow.java:26)
 *         ...
 * </pre>
 */
public class StackOverflow {

    private int stackLength = 1;

    public void doSomething() throws StackOverflowError {
        stackLength++;
        doSomething();
    }

    public static void main(String[] args) {

        // 자기 참조 StackOverflowError 발생
        // new ClassSelf();

        // 상호 참조 StackOverflowError 발생
        // new ClassOne();

        StackOverflow stackOverflow = new StackOverflow();
        try {
            stackOverflow.doSomething();
        } catch (Throwable e) {
            System.out.println("Depth of stack:" + stackOverflow.stackLength);
            // throw e;
        }
    }
}
