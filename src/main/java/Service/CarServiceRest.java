package Service;

import Controllers.DeleteOneCarHTTPHandler;
import Controllers.GetAllCarHTTPHandler;
import Controllers.GetOrPostOneCarHTTPHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class CarServiceRest{
    public CarServiceRest() throws IOException {
        int serverPort = 8888;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        server.createContext("/car", new GetOrPostOneCarHTTPHandler());
        server.createContext("/carall", new GetAllCarHTTPHandler());
        server.createContext("/cardelete", new DeleteOneCarHTTPHandler());
        server.setExecutor(null);
        server.start();
    }
}