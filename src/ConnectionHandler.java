/*
 * Copyright 2021 Damon Yu
 */

import java.net.Socket;

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

    }

    private void handle(HttpRequest request, HttpResponse response) {

    }

    private void doGet(HttpRequest request, HttpResponse response) {

    }

    private void doHead(HttpRequest request, HttpResponse response) {

    }
}
