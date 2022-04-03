package org.xpdojo.sof;

/**
 * 자기 참조 클래스
 *
 * <pre>
 *     Exception in thread "main" java.lang.StackOverflowError
 *      	at org.xpdojo.sof.ClassSelf.<init>(ClassSelf.java:10)
 *      	at org.xpdojo.sof.ClassSelf.<init>(ClassSelf.java:10)
 *      	at org.xpdojo.sof.ClassSelf.<init>(ClassSelf.java:10)
 *      	at org.xpdojo.sof.ClassSelf.<init>(ClassSelf.java:10)
 *          ...
 * </pre>
 */
public class ClassSelf {
    private ClassSelf classSelf;

    public ClassSelf() {
        classSelf = new ClassSelf();
    }
}
