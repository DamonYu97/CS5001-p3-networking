import java.io.IOException;
import java.io.OutputStream;

/**
 * HttpInputStreamWriter class writes http response back to client.
 * @author 200011181
 * @version 1.0
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
