/**
 * Configuration class stores the basic information to set up a webserver.
 * @author 200011181
 * @version 1.0
 */
public class Configuration {
    /**
     * server port
     */
    public static final int PORT = 80;
    /**
     * server root directory as absolute path
     */
    public static final String ROOT_DIRECTORY = "Resources/www";
    /**
     * server name
     */
    public static final String SERVER_NAME = "CS5001 P3 Networking Server";
    /**
     * not found page name as relative path
     */
    public static final String NOT_FOUND_PAGE = "/error/not_found.html";

    //To add your own not implemented page, uncomment following statement, and then add this to serverContent.
    //public static final String NOT_IMPLEMENTED_PAGE = <your html page>;

    /**
     * the number of thread for fixed thread pool in the server
     */
    public static final int NUMBER_OF_THREAD_POOL = 4;

    /**
     * server timeout in millisecond
     */
    public static final int TIMEOUT = 2000;
}
