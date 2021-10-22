/*
 * Copyright 2021 Damon Yu
 */

/**
 * @author ly40
 * @version 1.0
 * @since 22/10/2021
 */
public class WebServerMain {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: ClientMain <hostname>");
            System.exit(1);
        }
        WebServer webServer = new WebServer(args[0], args[1]);
    }
}
