package org.xpdojo.spring.beanlifecycle;

public class NetworkClientSetter {

    private String url;

    public NetworkClientSetter() {
        System.out.println("NetworkClient created with url");
        connect(); // Connecting to null
    }

    public void setUrl(String url) {
        this.url = url;
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
}
