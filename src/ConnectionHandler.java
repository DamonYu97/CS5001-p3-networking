/*
 * Copyright 2021 Damon Yu
 */

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * @author damonyu
 * @version 1.0
 * @since 24/10/2021
 */
public class ConnectionHandler implements Runnable {
    private static final String METHOD_HEAD = "HEAD";
    private static final String METHOD_GET = "GET";
    private static final String HTTP_PROTOCOL = "HTTP/1.1";
    private Socket connection;
    private ServerContext serverContext;

    public ConnectionHandler(Socket connection, ServerContext serverContext) {
        this.connection = connection;
        this.serverContext = serverContext;
    }

    @Override
    public void run() {
        while (!connection.isClosed()) {
            HttpRequest httpRequest = null;
            try {
                InputStream inputStream = connection.getInputStream();
                OutputStream outputStream = connection.getOutputStream();
                httpRequest = HttpInputStreamReader.readRequest(inputStream);
                if (httpRequest != null) {
                    System.out.println(httpRequest.getMethod() + " " + Thread.currentThread());
                    //TODO handle the request
                    HttpResponse response = handle(httpRequest);
                    HttpOutputStreamWriter.writeResponse(response, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    inputStream.close();
                    connection.close();
                }
            } catch (SocketException e) {
                System.out.println("Socket Exception: " + e.getMessage());
            } catch (IOException ioe) {
                System.err.println("IO Exception: " + ioe.getMessage());
            }
            try {
                Thread.sleep(2000); // pause before trying again ...
            } catch (InterruptedException e) {
                System.err.println("Interrupted Exception: " + e.getMessage());
            }
        }
        //System.out.println(currentThread() + "end");
    }

    private HttpResponse handle(HttpRequest request) {
        HttpResponseHeader responseHeader = new HttpResponseHeader(HTTP_PROTOCOL, serverContext.getName());
        HttpResponse response = new HttpResponse(responseHeader, mapFailPage(serverContext));
        String method = request.getMethod();
        if (METHOD_GET.equals(method)) {
            doGet(request, response);
        } else if (METHOD_HEAD.equals(method)) {
            doHead(request, response);
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
                //TODO content too long
                e.printStackTrace();
            }
        } else {
            response.fail(ResponseCode.NOT_FOUND);
        }
    }

    private void doHead(HttpRequest request, HttpResponse response) {
        doGet(request, response);
        response.removeContent();
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
