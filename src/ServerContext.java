/**
 * ServerContext class stores the information of a web server.
 * @author 200011181
 * @version 1.0
 */
public class ServerContext {
    private String name;
    private int port;
    private String rootDir;
    private String notFoundPage;
    private String notImplementedPage;
    private int threadPoolSize;
    private int timeout;

    public ServerContext(String name, int port, String rootDir) {
        this.name = name;
        this.port = port;
        this.rootDir = rootDir;
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

    public String getRootDir() {
        return rootDir;
    }

    public String getNotFoundPage() {
        return notFoundPage;
    }

    public void setNotFoundPage(String notFoundPage) {
        this.notFoundPage = notFoundPage;
    }

    public String getNotImplementedPage() {
        return notImplementedPage;
    }

    public void setNotImplementedPage(String notImplementedPage) {
        this.notImplementedPage = notImplementedPage;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
