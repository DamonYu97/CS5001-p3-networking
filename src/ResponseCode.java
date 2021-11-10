/**
 * ResponseCode enum lists all supported response code for HTTP.
 * @author 200011181
 * @version 1.0
 */
public enum ResponseCode {
    OK(200, "OK"),
    NOT_FOUND(404, "Not Found"),
    NOT_IMPLEMENTED(501, "Not Implemented");

    private final int code;
    private final String description;
    ResponseCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return code + " " + description;
    }
}
