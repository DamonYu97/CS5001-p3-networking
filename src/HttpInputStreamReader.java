/*
 * Copyright 2021 Damon Yu
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        Map<String, String> attributes = null;
        try {
            if (inputStream.available() > 0) {
                String[] lines = readLines(inputStream);
                if (lines == null || lines.length <= 0) {
                    return null;
                }
                String line = lines[0];
                int firstSpaceIndex = line.indexOf(' ');
                method = line.substring(0,firstSpaceIndex);
                int secondSpaceIndex = line.indexOf(' ', firstSpaceIndex + 1);
                resource = line.substring(firstSpaceIndex + 1, secondSpaceIndex);
                protocolVersion = line.substring(secondSpaceIndex + 1);
                attributes = new HashMap<>();
                for (int i = 1; i < lines.length; i++) {
                    line = lines[i];
                    int indexDelimiter = line.indexOf(":");
                    String name = line.substring(0, indexDelimiter);
                    String value = line.substring(indexDelimiter + 1);
                    attributes.put(name, value);
                }
            } else {
                return null;
            }
        } catch (IOException e) {
            System.err.println("Reader IOE Exception " + e.getMessage());
        }
        return new HttpRequest(method, resource, protocolVersion, attributes);
    }

    private static String[] readLines(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(inputStream));
        ArrayList<String> lines = new ArrayList<>();
        String line = null;
        while ((line = br.readLine()) != null && line.length() > 0) {
            lines.add(line);
            //System.out.println(line.length());
        }
        String[] result = new String[lines.size()];
        return lines.toArray(result);
    }

}
