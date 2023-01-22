package org.xpdojo.app.repository;

import org.xpdojo.app.Hello;

public interface HelloRepository {

    Hello findHello(String name);

    void increaseCount(String name);

    default int countOf(String name) {
        Hello hello = findHello(name);
        return hello == null ? 0 : hello.getCount();
    }

}
