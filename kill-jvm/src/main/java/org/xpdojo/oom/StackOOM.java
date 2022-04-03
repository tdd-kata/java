package org.xpdojo.oom;

/**
 * <pre>
 *     javac -Xlint:unchecked StackOOM.java
 *     java -Xss256k StackOOM
 * </pre>
 * <p>
 * System Monitoring 도구로 확인해보자.
 */
public class StackOOM {

    private static int threadNum = 0;

    public void doSomething() {
        try {
            Thread.sleep(100_000_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        final StackOOM stackOOM = new StackOOM();

        try {
            while (true) {
                threadNum++;
                Thread thread = new Thread(() -> stackOOM.doSomething());
                thread.start();
            }
        } catch (Throwable e) {
            System.out.println("Current number of active threads:" + threadNum);
            // throw e;
        }
    }
}
