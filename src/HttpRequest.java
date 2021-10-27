/*
 * Copyright 2021 Damon Yu
 */

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author damonyu
 * @version 1.0
 * @since 24/10/2021
 */
public class HttpRequest implements Serializable {
    private String method;
    private String resource;
    private String protocolVersion;
    private Map<String, String> attributes;

    public HttpRequest(String method, String resource, String protocolVersion, Map<String, String> attributes) {
        this.method = method;
        this.resource = resource;
        this.protocolVersion = protocolVersion;
        this.attributes = attributes;
    }

    public HttpRequest(String method, String resource, String protocolVersion) {
        this.method = method;
        this.resource = resource;
        this.protocolVersion = protocolVersion;
        this.attributes = new HashMap<>();
    }

    public String getMethod() {
        return method;
    }

    public String getResource() {
        return resource;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

}
