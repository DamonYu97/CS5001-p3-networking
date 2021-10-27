/*
 * Copyright 2021 Damon Yu
 */

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author damonyu
 * @version 1.0
 * @since 24/10/2021
 */
public class ConnectionHandler extends Thread{
    private static final String METHOD_HEAD = "HEAD";
    private static final String METHOD_GET = "GET";
    private Socket connection;
    private String rootDir;

    public ConnectionHandler(Socket connection, String rootDir) {
        this.connection = connection;
        this.rootDir = rootDir;
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
                    System.out.println(httpRequest.getMethod() + " " + currentThread());
                    //TODO handle the request
                    String method = httpRequest.getMethod();
                    String resourcePathString = httpRequest.getResource();
                    HttpResponse response = null;
                    if (METHOD_GET.equals(method)) {
                        //search the resource
                        Path resourcePath = Path.of(rootDir + resourcePathString);
                        if (Files.isDirectory(resourcePath)) {
                            resourcePath = resourcePath.resolve("index.html");
                        }
                        if (Files.exists(resourcePath)) {

                        } else {
                            response = HttpResponse.fail(HttpResponse.NOT_FOUND);
                        }
                    } else if (METHOD_HEAD.equals(method)) {

                    }
                }
            } catch (SocketException e) {
                System.out.println("Socket Exception: " + currentThread() + e.getMessage());
            } catch (IOException ioe) {
                System.err.println("IO Exception: " + currentThread()+ ioe.getMessage());
            }
            if (httpRequest == null) {
                try {
                    connection.close();
                    System.out.println("Terminating the connection");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(2000); // pause before trying again ...
            } catch (InterruptedException e) {
                System.err.println("Interrupted Exception: " + e.getMessage());
            }
        }
        //System.out.println(currentThread() + "end");
    }



    private void handle(HttpRequest request, HttpResponse response) {

    }

    private void doGet(HttpRequest request, HttpResponse response) {

    }

    private void doHead(HttpRequest request, HttpResponse response) {

    }

}
