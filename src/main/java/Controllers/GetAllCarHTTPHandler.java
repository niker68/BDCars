package Controllers;

import DB.DB;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class GetAllCarHTTPHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        if("GET".equals(httpExchange.getRequestMethod())) {
            try {
                handleResponse(httpExchange);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if("POST".equals(httpExchange.getRequestMethod())) {
        }

    }

    private String handleGetRequest(HttpExchange httpExchange) {
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
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String  htmlResponse;
        if (httpExchange.getRequestURI().toString().contains("?")) {
            String str0 = httpExchange.getRequestURI().toString().split("\\?")[1];
            String[] params = str0.split("&");
            Map<String, String> mapParam = new HashMap<String, String>();
            for (String param : params) {
                String name = param.split("=")[0];
                String value = param.split("=")[1];
                mapParam.put(name, value);
            }
            System.out.println(mapParam.toString());
              htmlResponse = ow.writeValueAsString(DB.readAll(mapParam.get("model"),mapParam.get("color")));
        }else {htmlResponse = ow.writeValueAsString(DB.readAll());}
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }

}
