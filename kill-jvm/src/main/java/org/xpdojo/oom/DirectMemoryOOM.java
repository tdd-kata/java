package org.xpdojo.oom;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * <pre>
 *      javac -Xlint:unchecked DirectMemoryOOM.java
 *      java -Xmx20M -XX:MaxDirectMemorySize=10M DirectMemoryOOM
 * </pre>
 *
 * <pre>
 *     Exception in thread "main" java.lang.OutOfMemoryError
 *         at java.base/jdk.internal.misc.Unsafe.allocateMemory(Unsafe.java:616)
 *         at jdk.unsupported/sun.misc.Unsafe.allocateMemory(Unsafe.java:462)
 *         at DirectMemoryOOM.main(DirectMemoryOOM.java:24)
 * </pre>
 */
public class DirectMemoryOOM {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args)
            throws IllegalArgumentException, IllegalAccessException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);

        while (true) {
            unsafe.allocateMemory(_1MB);
        }
    }
}
