package MainPackage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;

public class GetOrPostOneCarHTTPHandler implements HttpHandler  {
    int id;
    String htmlResponse;
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if(httpExchange.getRequestMethod().equals("GET")) {
            id = Integer.parseInt(handleGetParam(httpExchange));
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            try {
                Car car = DB.read(Integer.parseInt(handleGetParam(httpExchange)));
                htmlResponse = ow.writeValueAsString(car);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }
        else if("POST".equals(httpExchange.getRequestMethod())) {

            HashMap result;
            try (final BufferedReader in = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()))) {
                String inputLine;
                final StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                System.out.println(content);

                result = new ObjectMapper().readValue(content.toString(), HashMap.class);
                System.out.println(result);

                Car newcar = new Car();
                newcar.setGosnomer(result.get("gosnomer").toString());
                newcar.setModel(result.get("model").toString());
                newcar.setColor(result.get("color").toString());
                newcar.setYear(Integer.parseInt(result.get("year").toString()));
                boolean carAddedSuccess = DB.create(newcar);
                if (!DB.check(newcar)){
                    htmlResponse = "Error!";
                } else if (carAddedSuccess) {
                    htmlResponse = "Car added success";
                } else {htmlResponse = "Car already exists";
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }
            handleResponse(httpExchange);
    }

    private String handleGetParam(HttpExchange httpExchange) {
        return httpExchange.
                getRequestURI()
                .toString()
                .split("\\?")[1]
                .split("=")[1];
    }
    private void handleResponse(HttpExchange httpExchange) throws IOException{
        OutputStream outputStream = httpExchange.getResponseBody();
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
