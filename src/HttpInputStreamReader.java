import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

/**
 * HttpInputStreamReader class reads http request and add it to a HttpRequest object.
 * @author 200011181
 * @version 1.0
 */
public class HttpInputStreamReader {
    public static HttpRequest readRequest(InputStream inputStream) {
        String method = null;
        String resource = null;
        String protocolVersion = null;
        Map<String, String> attributes = new HashMap<>();
        Map<String, String> formData = new HashMap<>();
        try {
            if (inputStream.available() > 0) {
                String[] lines = readLines(inputStream);
                if (lines == null || lines.length <= 0) {
                    return null;
                }
                //get the first line of request information
                String line = lines[0];
                int firstSpaceIndex = line.indexOf(' ');
                method = line.substring(0, firstSpaceIndex);
                int secondSpaceIndex = line.indexOf(' ', firstSpaceIndex + 1);
                resource = line.substring(firstSpaceIndex + 1, secondSpaceIndex);
                protocolVersion = line.substring(secondSpaceIndex + 1);
                int index = 1;
                //store other request information.
                while (index < lines.length) {
                    line = lines[index];
                    index++;
                    if ("".equals(line)) {
                        break;
                    }
                    int indexDelimiter = line.indexOf(":");
                    String name = line.substring(0, indexDelimiter);
                    String value = line.substring(indexDelimiter + 1);
                    attributes.put(name, value);
                }
                //for post method, store form data.
                if (HttpRequest.METHOD_POST.equals(method)) {
                    while (index < lines.length) {
                        //pass the boundary information
                        index++;
                        if (index == lines.length) {
                            break;
                        }
                        //get one form data name
                        line = lines[index];
                        int nameStartIndex = line.indexOf('"');
                        int nameEndIndex = line.indexOf('"', nameStartIndex + 1);
                        String formDataName = line.substring(nameStartIndex + 1, nameEndIndex);
                        //pass an empty line
                        index++;
                        //get one form data value
                        index++;
                        line = lines[index];
                        String formDataValue = line.trim();
                        index++;
                        formData.put(formDataName, formDataValue);
                    }
                }
            } else {
                return null;
            }
        } catch (IOException e) {
            System.err.println("Reader IOE Exception " + e.getMessage());
        }
        return new HttpRequest(method, resource, protocolVersion, attributes, formData);
    }

    private static String[] readLines(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(inputStream));
        ArrayList<String> lines = new ArrayList<>();
        String line = br.readLine();
        int firstSpaceIndex = line.indexOf(' ');
        String method = line.substring(0, firstSpaceIndex);
        while (line != null) {
            lines.add(line);
            try {
                line = br.readLine();
                if (HttpRequest.METHOD_POST.equals(method)) {
                    //the end of post request: ---------mark--
                    if (line == null || (line.length() > 0 && line.charAt(line.length() - 1) == '-')) {
                        break;
                    }
                } else {
                    if (line == null || line.length() == 0) {
                        break;
                    }
                }

            } catch (IOException e) {
                //time out
                break;
            }
        }
        String[] result = new String[lines.size()];
        return lines.toArray(result);
    }

}
