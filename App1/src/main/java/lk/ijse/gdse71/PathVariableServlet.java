package lk.ijse.gdse71;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * --------------------------------------------
 * Author: Zeenathul Ilma
 * GitHub: https://github.com/Seenathul-Ilma
 * Website: https://zeenathulilma.vercel.app/
 * --------------------------------------------
 * Created: 5/29/2025 12:07 PM
 * Project: Examples
 * --------------------------------------------
 **/

@WebServlet("/path/*")
public class PathVariableServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // http://localhost:8080/App1_Web_exploded/path/ilma/kalutara/4693/hi  -> in postman - GET method - send
            /*  ilma
                kalutara
                4693
                hi
            */
        String pathInfo = req.getPathInfo();
        String[] parts = pathInfo.split("/");

        for (String part : parts) {
            System.out.println(part);
        }
    }
}
