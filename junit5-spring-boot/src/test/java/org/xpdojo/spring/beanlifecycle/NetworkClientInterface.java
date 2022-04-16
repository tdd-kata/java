package org.xpdojo.spring.beanlifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClientInterface implements InitializingBean, DisposableBean {

    private String url;

    public NetworkClientInterface(String url) {
        this.url = url;
        System.out.println("NetworkClient created with url");
    }

    public void connect() {
        System.out.println("Connecting to " + url);
    }

    public String send(String message) {
        return "Calling " + url + " : " + message;
    }

    public void disconnect() {
        System.out.println("Disconnecting from " + url);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("NetworkClient afterPropertiesSet");
        connect();
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("NetworkClient destroy");
        disconnect();
    }
}
