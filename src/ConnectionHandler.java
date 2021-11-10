import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Logger;

/**
 * ConnectionHandler class represents a runnable resource.
 * It handles the connection between clients and web server bsaed on the request information
 * @author 200011181
 * @version 1.0
 */
public class ConnectionHandler implements Runnable {
    private Socket connection;
    private ServerContext serverContext;
    private Logger logger;
    private List<String> methods;

    public ConnectionHandler(Socket connection, ServerContext serverContext) {
        this.connection = connection;
        this.serverContext = serverContext;
        logger = Logger.getLogger(serverContext.getName());
        //initialize the supported methods
        methods = new ArrayList<>();
        methods.add(HttpRequest.METHOD_GET);
        methods.add(HttpRequest.METHOD_HEAD);
        methods.add(HttpRequest.METHOD_POST);
        methods.add(HttpRequest.METHOD_OPTIONS);
    }

    @Override
    public void run() {
        while (!connection.isClosed()) {
            HttpRequest httpRequest = null;
            try {
                InputStream inputStream = connection.getInputStream();
                OutputStream outputStream = connection.getOutputStream();
                logger.info("Client in " + Thread.currentThread() + " - Reading the request......");
                System.out.println("Client in " + Thread.currentThread() + " - Reading the request......");
                //read the request
                httpRequest = HttpInputStreamReader.readRequest(inputStream);
                if (httpRequest != null) {
                    //handle the request
                    logger.info("Client in " + Thread.currentThread() + " - Request info: " + httpRequest);
                    HttpResponse httpResponse = handle(httpRequest);
                    logger.info("Client in " + Thread.currentThread() + " - Writing the response......");
                    System.out.println("Client in " + Thread.currentThread() + " - Writing the response......");
                    //write response information back to client
                    HttpOutputStreamWriter.writeResponse(httpResponse, outputStream);
                    logger.info("Client in " + Thread.currentThread() + " - Response info: "
                            + httpResponse.getResponseHeader().toString().trim());
                    //clean up
                    outputStream.close();
                    inputStream.close();
                    connection.close();
                }
            } catch (SocketException e) {
                System.out.println("Socket Exception: " + e.getMessage());
                logger.severe("Socket Exception: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("IO Exception: " + e.getMessage());
                logger.severe("IO Exception: " + e.getMessage());
            }
            try {
                // pause before trying again ...
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.err.println("Interrupted Exception: " + e.getMessage());
                logger.severe("Interrupted Exception: " + e.getMessage());
            }
        }
        if (connection.isClosed()) {
            System.out.println("Server closed the connection from Client in "
                    + Thread.currentThread() + " Address: " + connection.getInetAddress());
            logger.info("Server closed the connection from Client in "
                    + Thread.currentThread() + " Address: " + connection.getInetAddress());
        }
    }

    private HttpResponse handle(HttpRequest request) {
        HttpResponseHeader responseHeader = new HttpResponseHeader(HttpRequest.HTTP_1_1, serverContext.getName());
        HttpResponse response = new HttpResponse(responseHeader, mapFailPage(serverContext));
        String method = request.getMethod();
        if (HttpRequest.METHOD_GET.equals(method)) {
            doGet(request, response);
        } else if (HttpRequest.METHOD_HEAD.equals(method)) {
            doHead(request, response);
        } else if (HttpRequest.METHOD_POST.equals(method)) {
            doPost(request, response);
        } else if (HttpRequest.METHOD_OPTIONS.equals(method)) {
           doOptions(request, response);
        } else {
            response.fail(ResponseCode.NOT_IMPLEMENTED);
        }
        return response;
    }

    private void doGet(HttpRequest request, HttpResponse response) {
        //search the resource
        String resourcePathString = request.getResource();
        Path resourcePath = Path.of(serverContext.getRootDir() + resourcePathString);
        if (Files.isDirectory(resourcePath)) {
            resourcePath = resourcePath.resolve("index.html");
        }
        if (Files.exists(resourcePath)) {
            try {
                byte[] content = Files.readAllBytes(resourcePath);
                String contentType = Files.probeContentType(resourcePath);
                response.success(content, contentType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            response.fail(ResponseCode.NOT_FOUND);
        }
    }

    private void doPost(HttpRequest request, HttpResponse response) {
        doGet(request, response);
        if (response.getResponseCode() == ResponseCode.OK) {
            Map<String, String> data = request.getFormData();
            //deal with the data
            //in this post method, we just print data out
            //other post method process can be done by inheriting this connection handler
            byte[] dataByte = data.toString().getBytes();
            byte[] content = new byte[dataByte.length + response.getContent().length];
            System.arraycopy(dataByte, 0, content, 0, dataByte.length);
            System.arraycopy(response.getContent(), 0, content, dataByte.length, response.getContent().length);
            response.success(content, response.getContentType());
        }
    }

    private void doHead(HttpRequest request, HttpResponse response) {
        doGet(request, response);
        response.removeContent();
    }

    private void doOptions(HttpRequest request, HttpResponse response) {
        doHead(request, response);
        response.setSupportedMethods(methods);
    }

    private Map<Integer, Path> mapFailPage(ServerContext context) {
        Map<Integer, Path> map = new HashMap<>();
        Path path;
        if (context.getNotFoundPage() != null) {
            path = Path.of(context.getRootDir() + context.getNotFoundPage());
            map.put(ResponseCode.NOT_FOUND.getCode(), path);
        }
        if (context.getNotImplementedPage() != null) {
            path = Path.of(context.getRootDir() + context.getNotImplementedPage());
            map.put(ResponseCode.NOT_IMPLEMENTED.getCode(), path);
        }
        return map.isEmpty() ? null : map;
    }

}
