/*
 * Copyright 2021 Damon Yu
 */

import java.io.File;
import java.nio.file.Path;

/**
 * @author ly40
 * @version 1.0
 * @since 22/10/2021
 */
public class WebServerMain {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java WebServerMain <document_root> <port>");
            System.exit(1);
        }
        String rootDir = args[0];
        File root = new File(rootDir);
        if (!root.exists()) {
            System.err.println("Root directory not found!");
            System.exit(1);
        }
        int port = Integer.parseInt(args[1]);
        String serverName = "Damon Java Web Server";
        ServerContext serverContext = new ServerContext(serverName, port, rootDir);
        serverContext.setNotFoundPage(Configuration.NOT_FOUND_PAGE);
        serverContext.setThreadPoolSize(Configuration.NUMBER_OF_THREAD_POOL);
        WebServer webServer = new WebServer(serverContext);
        webServer.start();
    }
}
