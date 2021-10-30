/*
 * Copyright 2021 Damon Yu
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ly40
 * @version 1.0
 * @since 22/10/2021
 */
public class WebServer {
    private ServerSocket serverSocket;
    private ServerContext serverContext;
    private ExecutorService executorService;

    public WebServer(ServerContext serverContext) {
        this.serverContext = serverContext;
        executorService = Executors.newFixedThreadPool(serverContext.getThreadPoolSize());
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(serverContext.getPort());
            System.out.println("Server started ... listening on port " + serverContext.getPort() + " ...");
            Socket conn = null;
            while (true) {
                // waits until client requests a connection, then returns connection (socket)
                try {
                    conn = serverSocket.accept();
                    conn.setSoTimeout(2000);
                    System.out.println("Server got new connection request from " + conn.getInetAddress());
                } catch (IOException ioe) {
                    System.err.println("IO Exception: " + ioe.getMessage());
                }
                // create new handler for this connection
                executorService.execute(new ConnectionHandler(conn, serverContext));
                try {
                    Thread.sleep(2000); // pause before trying again ...
                } catch (InterruptedException e) {
                    System.err.println("Interrupted Exception: " + e.getMessage());
                }
            }
        } catch (IOException ioe) {
            System.out.println("Oops " + ioe.getMessage());
        }
    }
}
