import java.io.File;

/**
 * Practical 3 Networking WebServerMain.
 * @author 200011181
 * @version 1.0
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
            System.exit(2);
        }
        int port = -1;
        try {
          port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Usage: java WebServerMain <document_root> <port>");
            System.err.println("Wrong format of port number");
            System.exit(1);
        }
        //initialize the serverContext with server information
        ServerContext serverContext = new ServerContext(Configuration.SERVER_NAME, port, rootDir);
        serverContext.setNotFoundPage(Configuration.NOT_FOUND_PAGE);
        serverContext.setThreadPoolSize(Configuration.NUMBER_OF_THREAD_POOL);
        serverContext.setTimeout(Configuration.TIMEOUT);
        //create a webserver
        WebServer webServer = new WebServer(serverContext);
        //start webserver
        webServer.start();
    }
}
