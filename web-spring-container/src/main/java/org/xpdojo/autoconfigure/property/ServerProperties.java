package org.xpdojo.autoconfigure.property;

/**
 * @see org.springframework.boot.autoconfigure.web.ServerProperties
 */
@MyConfigurationProperties(prefix = "localhost")
public class ServerProperties {

    private String contextPath;
    private int port;

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
