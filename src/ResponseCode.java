/*
 * Copyright 2021 Damon Yu
 */

/**
 * @author ly40
 * @version 1.0
 * @since 27/10/2021
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
