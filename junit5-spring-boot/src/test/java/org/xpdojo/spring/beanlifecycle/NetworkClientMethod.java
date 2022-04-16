package org.xpdojo.spring.beanlifecycle;

public class NetworkClientMethod {

    private String url;

    public NetworkClientMethod(String url) {
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

    public void init() {
        System.out.println("NetworkClient init");
        connect();
    }

    public void close() {
        System.out.println("NetworkClient destroy");
        disconnect();
    }
}
