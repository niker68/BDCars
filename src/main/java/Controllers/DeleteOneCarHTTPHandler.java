package Controllers;

import DB.DB;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public class DeleteOneCarHTTPHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if("DELETE".equals(httpExchange.getRequestMethod())) {
            try {
                handleResponse(httpExchange);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if("POST".equals(httpExchange.getRequestMethod())) {
        } else if("GET".equals(httpExchange.getRequestMethod())){
        }


    }

    private String handleGetParam(HttpExchange httpExchange) {
        return httpExchange.
                getRequestURI()
                .toString()
                .split("\\?")[1]
                .split("=")[1];
    }

    /*private String handlePostRequest(HttpExchange httpExchange) {
        return httpExchange.
                getRequestURI()
                .toString()
                .split("\\?")[1]
                .split("=")[1];
    }*/

    private void handleResponse(HttpExchange httpExchange) throws IOException, SQLException, ClassNotFoundException {
        OutputStream outputStream = httpExchange.getResponseBody();
        System.out.println(Integer.parseInt(handleGetParam(httpExchange)));
        DB.delete(Integer.parseInt(handleGetParam(httpExchange)));
        String  htmlResponse = "Car with id = "+ handleGetParam(httpExchange) +" deleted";
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }

}
