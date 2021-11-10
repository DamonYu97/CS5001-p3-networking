import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.*;

/**
 * Webserver class represents the behavior of a web server.
 * @author 200011181
 * @version 1.0
 */
public class WebServer {
    private static final String LOGFILE_DIRECTORY = "log";
    private Logger logger;
    private ServerSocket serverSocket;
    private ServerContext serverContext;
    private ExecutorService executorService;

    public WebServer(ServerContext serverContext) {
        this.serverContext = serverContext;
        //Initial a fixed Thread Pool
        executorService = Executors.newFixedThreadPool(serverContext.getThreadPoolSize());
        //Initial logger
        logger = Logger.getLogger(serverContext.getName());
        logger.setUseParentHandlers(false);
        try {
            //creat a file for log information
            String logFileName = getLogFileName();
            //creat a file handler for logger without overwrite the existing log file
            FileHandler fileHandler = new FileHandler(logFileName, true);
            fileHandler.setFormatter(new WebServerLoggerFormatter());
            //add file handler to logger
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void start() {
        try {
            serverSocket = new ServerSocket(serverContext.getPort());
            logger.info("Server started ... listening on port " + serverContext.getPort() + " ...");
            System.out.println("Server started ... listening on port " + serverContext.getPort() + " ...");
            Socket conn = null;
            while (true) {
                // waits until client requests a connection, then returns connection
                try {
                    conn = serverSocket.accept();
                    conn.setSoTimeout(serverContext.getTimeout());
                    logger.info("Server got new connection request from " + conn.getInetAddress());
                    System.out.println("Server got new connection request from " + conn.getInetAddress());
                } catch (IOException ioe) {
                    System.err.println("Get connection IO Exception: " + ioe.getMessage());
                    logger.severe("Get connection IO Exception: " + ioe.getMessage());
                }
                //creat a thread to handler the connection
                executorService.execute(new ConnectionHandler(conn, serverContext));
                try {
                    // pause before trying again ...
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.err.println("Interrupted Exception: " + e.getMessage());
                    logger.severe("Interrupted Exception: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Oops " + e.getMessage());
            logger.severe("Oops " + e.getMessage());
        }
    }

    private String getLogFileName() throws IOException {
        if (!Files.exists(Path.of(LOGFILE_DIRECTORY))) {
            Files.createDirectory(Path.of(LOGFILE_DIRECTORY));
        }
        return LOGFILE_DIRECTORY + "/" + serverContext.getName() + ".log";
    }
}
