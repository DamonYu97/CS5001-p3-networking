import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * HttpRequest class stores http response information and behavior.
 * @author 200011181
 * @version 1.0
 */
public class HttpResponse implements Serializable {
    private HttpResponseHeader responseHeader;
    private byte[] content;
    /**
     * Map the fail code with related web page file path.
     * use Path instead of String, because those paths can be validated when initializing the server.
     */
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
            if (failPagePath != null && Files.exists(failPagePath) && !Files.isDirectory(failPagePath)) {
                try {
                    content = Files.readAllBytes(failPagePath);
                    responseHeader.setContentType(Files.probeContentType(failPagePath));
                    responseHeader.setContentLength(content.length);
                    responseHeader.setResponseCode(responseCode);
                    return;
                } catch (IOException e) {
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

    public String getContentType() {
        return responseHeader.getContentType();
    }

    public ResponseCode getResponseCode() {
        return responseHeader.getResponseCode();
    }

    public void setSupportedMethods(List<String> supportedMethods) {
        this.responseHeader.setSupportedMethods(supportedMethods);
    }
}
