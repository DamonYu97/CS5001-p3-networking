/*
 * Copyright 2021 Damon Yu
 */

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author damonyu
 * @version 1.0
 * @since 29/10/2021
 */
public class HttpOutputStreamWriter {
    public static void writeResponse(HttpResponse response, OutputStream outputStream) throws IOException {
        outputStream.write(response.getResponseHeader().toString().getBytes());
        outputStream.write("\r\n".getBytes());
        if (response.getContent() != null ) {
            outputStream.write(response.getContent());
        }
    }
}
