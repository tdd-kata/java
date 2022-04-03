package org.xpdojo.oom;

/**
 * Permanent Heap은 Java 8부터 Native Memory 영역으로 이동하여 Metaspace 영역으로 변경되었다.
 *
 * <pre>
 *     javac -encoding UTF-8 ArraySizeOOM.java
 *     java -Xms2m -Xmx2m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./ ArraySizeOOM
 * </pre>
 * <p>
 * .hprof 파일은 IntelliJ IDEA에서 열 수 있다.
 *
 * <pre>
 *     java.lang.OutOfMemoryError: Requested array size exceeds VM limit
 *     Dumping heap to ./\java_pid206312.hprof ...
 *     Heap dump file created [2140940 bytes in 0.008 secs]
 *     java.lang.OutOfMemoryError: Requested array size exceeds VM limit
 *             at ArraySizeOOM.main(ArraySizeOOM.java:29)
 * </pre>
 *
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/troubleshoot/memleaks002.html#sthref41">Understand the OutOfMemoryError Exception</a>
 */
public class ArraySizeOOM {

    public static void main(String[] args) {
        try {
            int[] arr = new int[Integer.MAX_VALUE];
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
