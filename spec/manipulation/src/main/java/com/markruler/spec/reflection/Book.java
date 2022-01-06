package com.markruler.spec.reflection;

@MyAnnotation
public class Book {

    private String title;
    private String privateString = "private";

    private static String privateStatic = "privateStatic value";

    private static final String privateStaticFinal = "privateStaticFinal";

    public String publicString = "public value";

    protected String protectedString = "protected";

    public Book() {
    }

    public Book(String title) {
        this.title = title;
    }

    public Book(String privateString, String publicString, String protectedString) {
        this.privateString = privateString;
        this.publicString = publicString;
        this.protectedString = protectedString;
    }

    private void privateVoid() {
        System.out.println("privateVoid");
    }

    public void publicVoid() {
        System.out.println("publicVoid");
    }

    public int publicInt() {
        return 100;
    }

    public String getTitle() {
        return title;
    }
}
