/*
 * Copyright 2021 Damon Yu
 */

import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * @author damonyu
 * @version 1.0
 * @since 24/10/2021
 */
public class HttpResponse implements Serializable {
    public static final int OK = 200;
    public static final int NOT_FOUND = 404;
    private static final int NOT_IMPLEMENTED = 501;
    private HttpResponseHeader responseHeader;
    private ArrayList<Byte> content;

    private HttpResponse() {
    }

    public static HttpResponse success(Path contentPath, String serverName, String protocolName) {

        return new HttpResponse();
    }

    public static HttpResponse fail(int code) {
        ResponseCode responseCode = null;
        switch (code) {
            case NOT_FOUND:
                responseCode = ResponseCode.NOT_FOUND;
                break;
            default:
                responseCode = ResponseCode.NOT_IMPLEMENTED;
        }
        return new HttpResponse();
    }

}
