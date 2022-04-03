import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * $ javac -Xlint:unchecked HeapOOM.java
 * $ java -Xms10m -Xmx10m HeapOOM
 * </pre>
 *
 * <pre>
 * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
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
