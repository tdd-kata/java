package org.xpdojo.spring.testdouble;

public class MockitoDoubleImpl implements MockitoDouble {
    @Override
    public String name(String name) {
        return "Hello " + name;
    }
}
