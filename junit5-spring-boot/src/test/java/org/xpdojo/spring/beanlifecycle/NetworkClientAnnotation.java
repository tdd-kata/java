package org.xpdojo.spring.beanlifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClientAnnotation {

    private String url;

    public NetworkClientAnnotation(String url) {
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

    @PostConstruct
    public void init() {
        System.out.println("NetworkClient init");
        connect();
    }

    @PreDestroy
    public void close() {
        System.out.println("NetworkClient destroy");
        disconnect();
    }
}
