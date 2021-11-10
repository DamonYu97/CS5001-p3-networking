import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * HttpRequest class stores http request information.
 * @author 200011181
 * @version 1.0
 */
public class HttpRequest implements Serializable {
    public static final String METHOD_HEAD = "HEAD";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_OPTIONS = "OPTIONS";
    public static final String METHOD_POST = "POST";
    public static final String HTTP_1_1 = "HTTP/1.1";
    private String method;
    private String resource;
    private String protocolVersion;
    private Map<String, String> attributes;
    private Map<String, String> formData;

    public HttpRequest(String method, String resource, String protocolVersion,
                       Map<String, String> attributes, Map<String, String> formData) {
        this.method = method;
        this.resource = resource;
        this.protocolVersion = protocolVersion;
        this.attributes = attributes;
        this.formData = formData;
    }

    public HttpRequest(String method, String resource, String protocolVersion) {
        this(method, resource, protocolVersion, new HashMap<>(), new HashMap<>());
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

    public Map<String, String> getFormData() {
        return formData;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public void setFormData(Map<String, String> formData) {
        this.formData = formData;
    }

    @Override
    public String toString() {
        String formDataInfo = "";
        if (formData.size() > 0) {
            formDataInfo = "\nForm Data = " + formData;
        }

        return method + " " + resource + " " + protocolVersion + '\n'
                + "Attributes = " + attributes + formDataInfo;
    }
}
