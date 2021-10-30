/*
 * Copyright 2021 Damon Yu
 */

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * @author damonyu
 * @version 1.0
 * @since 24/10/2021
 */
public class HttpResponse implements Serializable {
    private HttpResponseHeader responseHeader;
    private byte[] content;
    //use Path instead of String, because those paths can be validated when initializing the server.
    private transient Map<Integer, Path> failMapper;

    public HttpResponse(HttpResponseHeader httpResponseHeader) {
        this(httpResponseHeader, null);
    }

    public HttpResponse(HttpResponseHeader httpResponseHeader, Map<Integer, Path> failMapper) {
        this.responseHeader = httpResponseHeader;
        this.failMapper = failMapper;
    }

    public void success(byte[] content, String contentType) {
        this.content = content;
        responseHeader.setContentLength(content.length);
        responseHeader.setContentType(contentType);
        responseHeader.setResponseCode(ResponseCode.OK);
    }

    public void fail(ResponseCode responseCode) {
        if (failMapper != null) {
            Path failPagePath = failMapper.get(responseCode.getCode());
            if (failPagePath!= null && Files.exists(failPagePath) && !Files.isDirectory(failPagePath)) {
                try {
                    content = Files.readAllBytes(failPagePath);
                    responseHeader.setContentType(Files.probeContentType(failPagePath));
                    responseHeader.setContentLength(content.length);
                    responseHeader.setResponseCode(responseCode);
                    return;
                } catch (IOException e) {
                    //TODO content too long
                    e.printStackTrace();
                }
            }
        }

        switch (responseCode) {
            case NOT_FOUND:
                fail(responseCode, "The requested resources are not found!");
                break;
            default:
                fail(responseCode, "Unsupported Service!");
        }
    }

    public void fail(ResponseCode responseCode, String message) {
        content = message.getBytes();
        responseHeader.setContentLength(content.length);
        responseHeader.setContentType("text/html");
        responseHeader.setResponseCode(responseCode);
    }

    public void removeContent() {
        content = null;
    }

    public HttpResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public byte[] getContent() {
        return content;
    }
}
