package org.xpdojo.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *      javac -Xlint:unchecked HeapOOM.java
 *
 *      java -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$CATALINA_HOME/logs HeapOOM
 *      [warning][gc] -XX:+PrintGCDetails is deprecated. Will use -Xlog:gc* instead.
 *
 *      java -Xms10m -Xmx10m -Xloggc:$CATALINA_BASE/logs/gc.log -Xlog:gc* -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$CATALINA_BASE/jvm.hprof HeapOOM
 * </pre>
 *
 * <pre>
 *     Dumping heap to C:\Users\imcxs/logs ...
 *     Heap dump file created [13698418 bytes in 0.038 secs]
 *     Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 *         at java.base/java.util.Arrays.copyOf(Arrays.java:3720)
 *         at java.base/java.util.Arrays.copyOf(Arrays.java:3689)
 *         at java.base/java.util.ArrayList.grow(ArrayList.java:238)
 *         at java.base/java.util.ArrayList.grow(ArrayList.java:243)
 *         at java.base/java.util.ArrayList.add(ArrayList.java:486)
 *         at java.base/java.util.ArrayList.add(ArrayList.java:499)
 *         at HeapOOM.main(HeapOOM.java:27)
 * </pre>
 */
public class HeapOOM {

    static class Zombie {
    }

    public static void main(String[] args) {

        List<Zombie> zombies = new ArrayList<>();

        while (true) {
            zombies.add(new Zombie());
        }

    }
}
