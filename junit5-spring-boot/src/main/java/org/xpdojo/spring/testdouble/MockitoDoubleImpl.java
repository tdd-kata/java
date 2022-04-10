package org.xpdojo.spring.testdouble;

public class MockitoDoubleImpl implements MockitoDouble {
    private String name;

    @Override
    public String greeting(String name) {
        this.name = name;
        return "Hello " + name;
    }

    public String getName() {
        return name;
    }
}
