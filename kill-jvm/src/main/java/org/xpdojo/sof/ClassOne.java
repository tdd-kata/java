package org.xpdojo.sof;

/**
 * 상호 참조 클래스
 *
 * <pre>
 *     Exception in thread "main" java.lang.StackOverflowError
 *      	at org.xpdojo.sof.ClassTwo.<init>(ClassTwo.java:10)
 *      	at org.xpdojo.sof.ClassOne.<init>(ClassOne.java:10)
 *      	at org.xpdojo.sof.ClassTwo.<init>(ClassTwo.java:10)
 *      	at org.xpdojo.sof.ClassOne.<init>(ClassOne.java:10)
 *      	at org.xpdojo.sof.ClassTwo.<init>(ClassTwo.java:10)
 *      	at org.xpdojo.sof.ClassOne.<init>(ClassOne.java:10)
 *      	at org.xpdojo.sof.ClassTwo.<init>(ClassTwo.java:10)
 *      	at org.xpdojo.sof.ClassOne.<init>(ClassOne.java:10)
 *          ...
 * </pre>
 */
public class ClassOne {
    private ClassTwo classTwo;

    public ClassOne() {
        classTwo = new ClassTwo();
    }
}
