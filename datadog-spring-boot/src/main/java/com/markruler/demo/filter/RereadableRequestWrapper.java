package com.markruler.demo.filter;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * <a href="https://github.com/apache/tomcat/blob/7.0.0/java/org/apache/catalina/connector/Request.java#L1173-L1186">Tomcat
 * </a>(Servlet Container)에서 InputStream을 이미 읽었다면 Reader도 읽지 못하게 막아놓았다.
 *
 * @see org.apache.catalina.connector.Request#getReader()
 * @see org.apache.catalina.connector.Request#getInputStream()
 */
public class RereadableRequestWrapper extends HttpServletRequestWrapper {
    private ByteArrayOutputStream cachedBytes;
    private final Charset encoding;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public RereadableRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        String characterEncoding = request.getCharacterEncoding();
        if (!StringUtils.hasText(characterEncoding)) {
            characterEncoding = StandardCharsets.UTF_8.name();
        }
        this.encoding = Charset.forName(characterEncoding);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (cachedBytes == null) {
            cacheInputStream();
        }
        return new CachedServletInputStream(cachedBytes.toByteArray());
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream(), this.encoding));
    }

    private void cacheInputStream() throws IOException {
        cachedBytes = new ByteArrayOutputStream();
        IOUtils.copy(super.getInputStream(), cachedBytes);
    }

    // An inputstream which reads the cached request body
    public class CachedServletInputStream extends ServletInputStream {
        private ByteArrayInputStream buffer;

        public CachedServletInputStream(byte[] contents) {
            // create a new input stream from the cached request body
            buffer = new ByteArrayInputStream(contents);
        }

        @Override
        public int read() throws IOException {
            return buffer.read();
        }

        @Override
        public boolean isFinished() {
            return buffer.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            throw new RuntimeException("Not implemented");
        }
    }
}
