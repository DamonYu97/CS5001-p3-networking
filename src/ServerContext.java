/*
 * Copyright 2021 Damon Yu
 */

/**
 * @author damonyu
 * @version 1.0
 * @since 28/10/2021
 */
public class ServerContext {
    private String name;
    private int port;
    private String rootDir;
    private String notFoundPage;
    private String notImplementedPage;
    private int threadPoolSize;

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
}
