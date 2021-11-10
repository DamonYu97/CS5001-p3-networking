import java.io.Serializable;
import java.util.*;

/**
 * HttpResponseHeader class stores http response header information.
 * @author 200011181
 * @version 1.0
 */
public class HttpResponseHeader implements Serializable {
    private String protocol;
    private ResponseCode responseCode;
    private String server;
    private int contentLength;
    private String contentType;
    private List<String> supportedMethods;

    public HttpResponseHeader(String protocol, ResponseCode responseCode, String server,
                              int contentLength, String contentType, List<String> supportedMethods) {
        this.protocol = protocol;
        this.responseCode = responseCode;
        this.server = server;
        this.contentLength = contentLength;
        this.contentType = contentType;
        this.supportedMethods = List.copyOf(supportedMethods);
    }

    public HttpResponseHeader(String protocol, String server) {
        this(protocol, null, server, 0, null, new ArrayList<>());
    }

    public String getContentType() {
        return contentType;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
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

    public void setSupportedMethods(List<String> supportedMethods) {
        this.supportedMethods = List.copyOf(supportedMethods);
    }

    @Override
    public String toString() {
        String supportedMethodsNames = "";
        if (supportedMethods.size() > 0) {
            supportedMethodsNames += "Allow: ";
            for (int i = 0; i < supportedMethods.size(); i++) {
                supportedMethodsNames += supportedMethods.get(i);
                if (i != supportedMethods.size() - 1) {
                    supportedMethodsNames += ", ";
                }
            }
            supportedMethodsNames += "\r\n";
        }
        return protocol + " " +  responseCode + "\r\n"
                + supportedMethodsNames
                + "Server: " + server + "\r\n"
                + "Content-Type: " + contentType + "\r\n"
                + "Content-Length: " + contentLength + "\r\n";
    }

}
