package jettytest.server;

import jettytest.forms.RegisterRequest;
import jettytest.forms.XMLConverter;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Collectors;

public class HttpResponseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        String xmlRequest =request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        XMLConverter  converter = new XMLConverter();
        RegisterRequest registerRequest = (RegisterRequest) converter.deserialize(xmlRequest, RegisterRequest.class);
        if(registerRequest.getType().equals("registerCustomer")){
            String str = registerRequest.getLogin();
        }
    }



}

