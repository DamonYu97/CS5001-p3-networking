/*
 * Copyright 2021 Damon Yu
 */

import java.io.Serializable;

/**
 * @author ly40
 * @version 1.0
 * @since 27/10/2021
 */
public class HttpResponseHeader implements Serializable {

    private String protocol;
    private ResponseCode responseCode;
    private String server;
    private int contentLength;
    private String contentType;

    public HttpResponseHeader(String protocol, ResponseCode responseCode, String server, int contentLength, String contentType) {
        this.protocol = protocol;
        this.responseCode = responseCode;
        this.server = server;
        this.contentLength = contentLength;
        this.contentType = contentType;
    }

    public HttpResponseHeader(String protocol, String server) {
        this(protocol, null, server, 0, null);
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return protocol + " " +  responseCode + "\r\n"
                + "Server: " + server + "\r\n"
                + "Content-Type: " + contentType + "\r\n"
                + "Content-Length: " + contentLength + "\r\n";
    }

}
