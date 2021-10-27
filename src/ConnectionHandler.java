/*
 * Copyright 2021 Damon Yu
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author damonyu
 * @version 1.0
 * @since 24/10/2021
 */
public class ConnectionHandler extends Thread{
    private static final String METHOD_HEAD = "HEAD";
    private static final String METHOD_GET = "GET";
    private Socket connection;

    public ConnectionHandler(Socket connection) {
        this.connection = connection;
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
