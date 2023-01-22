package org.xpdojo.app;

import org.xpdojo.container.MySpringApplication;
import org.xpdojo.container.MySpringBootApplication;

@MySpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        MySpringApplication.run(DemoApplication.class, args);
    }

}
