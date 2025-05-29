package lk.ijse.gdse71;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/mime")
@MultipartConfig
public class MimeTypes extends HttpServlet {
    // 1
    /*@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //read text/plain data from http request body  - raw
        String body = new BufferedReader(new InputStreamReader(
                req.getInputStream())).
                lines().collect(Collectors.joining("\n"));

        resp.setContentType("text/plain");
        resp.getWriter().write(body);
    }*/

    // 2
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        resp.setContentType("text/plain");
        resp.getWriter().write(name + " - " + address);
    }

    // 3
    /*@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Part part = req.getPart("file");
        String fileName = part.getSubmittedFileName();
        resp.setContentType("text/plain");
        resp.getWriter().write(name + " - " + fileName);
    }*/

    /*@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");

        String name = req.getParameter("name");
        String address = req.getParameter("address");
        resp.getWriter().write(name + " - " + address);
        System.out.println("Received POST request");
        System.out.println("name: " + req.getParameter("name"));
        System.out.println("address: " + req.getParameter("address"));


        for (Part part : req.getParts()) {
            // Skip form fields
            if (part.getSubmittedFileName() == null) continue;

            String fileName = part.getSubmittedFileName();
            String uploadPath = "C:/IJSE/AAD/uploadFiles/" + fileName;   // Change path as needed

            part.write(uploadPath); // Save file

            resp.getWriter().write("Saved: " + fileName + "\n");
        }
    }
*/

    // 4
    // raw - JSON
    // JSON Object
    // read JSON data form httpRequest body
    /*@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(req.getReader());

        String name = jsonNode.get("name").asText();
        String address = jsonNode.get("address").asText();

        resp.setContentType("text/plain");
        resp.getWriter().write(name+" - "+address);
    }*/

    // 5
    // raw - JSON
    // JSON Array
    // Method 1 -> read JSON Array data form httpRequest body
    /*@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<JsonNode> users = mapper.readValue(req.getReader(), new TypeReference<List<JsonNode>>(){});
        for (JsonNode user : users) {
            String name = user.get("name").asText();
            String address = user.get("address").asText();
            System.out.println(name + " - " + address);
        }
        resp.setContentType("text/plain");
        resp.getWriter().println("OK");
    }*/

    // raw - JSON
    // JSON Array
    // Method 2 -> read JSON Array data form httpRequest body
    /*@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(req.getReader());

        if (jsonNode.isArray()){
            for (int i = 0; i < jsonNode.size(); i++) {
                JsonNode node = jsonNode.get(i);

                String name = node.get("name").asText();
                String address = node.get("address").asText();

                resp.setContentType("text/plain");
                resp.getWriter().write(name + " " + address + "\n");
            }
        }
    }*/
}
