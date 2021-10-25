/*
 * Copyright 2021 Damon Yu
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author ly40
 * @version 1.0
 * @since 25/10/2021
 */
public class HttpInputStreamReader {
    public static HttpRequest readRequest(InputStream inputStream) {
        String method = null;
        String resource = null;
        String protocolVersion = null;

        try {
            if (inputStream.available() > 0) {
                BufferedReader br = new BufferedReader(new java.io.InputStreamReader(inputStream));
                String line = br.readLine();
                int firstSpaceIndex = line.indexOf(' ');
                method = line.substring(0,firstSpaceIndex);
                int secondSpaceIndex = line.indexOf(' ', firstSpaceIndex + 1);
                resource = line.substring(firstSpaceIndex + 1, secondSpaceIndex);
                protocolVersion = line.substring(secondSpaceIndex + 1);
            } else {
                return null;
            }
        } catch (IOException e) {
            System.err.println("ByteReader.readLine() - error: " + e.getMessage());
        }
        return new HttpRequest(method, resource, protocolVersion);
    }
}
