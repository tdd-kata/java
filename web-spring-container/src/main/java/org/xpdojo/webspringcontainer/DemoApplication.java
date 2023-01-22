package org.xpdojo.webspringcontainer;

import org.xpdojo.webspringcontainer.container.MySpringApplication;
import org.xpdojo.webspringcontainer.container.MySpringBootApplication;

@MySpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        MySpringApplication.run(DemoApplication.class, args);
    }

}
