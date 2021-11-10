1. You can change the server informaton in Configuration.java file
	For example, port, server name. Since this pratical requires port number and root directory from args, these two information in Configuration file will not be used. But you can set it up in WebServerMain.
2. You can set up your own fail repponse pages (not found, not implemented) by adding its relative path to Configuration file and set it to ServerContext in WebServerMain. If you don't specify one, it will use default messages.
3. The log file will be stored in log directory under the root directory you set up. And the file name will be your server name.
4. If you want to handle request information differently, you can inherit the ConnectionHandler class, override the doGet, doHead, doPost, doOptions Method.
5. All request information is stored in an HttpRequest Object, and the map attributes stores other information which have not yet used in this practical.
