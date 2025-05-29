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
 * Created: 5/29/2025 2:10 PM
 * Project: Examples
 * --------------------------------------------
 **/

@WebServlet("/lifecycles")
public class ServletLifeCycles extends HttpServlet {
    @Override
    public void init() throws ServletException {
        System.out.println("ServletLifeCycles init");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ServletLifeCycles doGet");
    }

    @Override
    public void destroy() {
        System.out.println("ServletLifeCycles destroy");
    }
}
