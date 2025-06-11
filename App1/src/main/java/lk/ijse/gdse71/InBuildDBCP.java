package lk.ijse.gdse71;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * --------------------------------------------
 * Author: Zeenathul Ilma
 * GitHub: https://github.com/Seenathul-Ilma
 * Website: https://zeenathulilma.vercel.app/
 * --------------------------------------------
 * Created: 6/6/2025 10:22 AM
 * Project: Examples
 * --------------------------------------------
 **/

@WebServlet("/inbuild")
public class InBuildDBCP extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource ds;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection connection = ds.getConnection();
            ResultSet resultSet = connection.prepareStatement("select * from event").executeQuery();
            List<Map<String, String >> elist = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, String> event = new HashMap<>();
                event.put("eid", resultSet.getString("eid"));
                event.put("ename", resultSet.getString("ename"));
                event.put("edescription", resultSet.getString("edescription"));
                event.put("edate", resultSet.getString("edate"));
                event.put("eplace", resultSet.getString("eplace"));
                elist.add(event);
            }
            resp.setContentType("application/json");
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(resp.getWriter(), elist);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

//  1. Create class InBuildDBCP and extent it by HTTPServlet
//  2. Add @WebServlet annotation
//  3. Go to project structure
//  4. Click on Modules
//  5. Select web (facet) inside App1 module
//  6. Click on 'Add Application Server-specific Descriptor'
//  7. Select Tomcat Server and click on 'OK'
//  8. Click on 'Apply' Button
//  9. Go to context.xml inside App1's web folder's 'META-INF' folder. If not context.xml file there, create a file named as 'context.xml'
// 10. In context.xml, add a resource tage and set attributes to that tag (See <Resource> tag in context.xml)
// 11. No Again come to InBuildDBCP class and add Datasource as private variable and also, add an annotation as "@Resource"
// 12. If not showing an annotation as "@Resource", then want to add external dependency "Jakarta Annotations API" - latest version
// 13. (name = "java:comp/env/jdbc/pool") -> Add this url to @Resource  (java compile environment -> jdbc/pool (dynamic, not constant, can change), by this can identify the resource. Bcz, we can add multiple resources. so names must be unique)
// 14. create doGet method and code
// 15. check on post api by GET



/*
💡 Why Use In-Built DBCP?
DBCP stands for Database Connection Pooling.
It means: instead of creating a new DB connection every time, we reuse a few open connections — it's much faster and efficient.

✅ Benefits of using DBCP:
| Without Pooling                                | With DBCP Pooling (e.g., Apache DBCP) |
| ---------------------------------------------- | ------------------------------------- |
| Creates new DB connection every time – 🐢 slow | Reuses existing connections – ⚡ fast  |
| More resource usage (RAM/CPU)                  | Efficient resource management         |
| Risk of `Too many connections` error           | Controlled max connections (e.g. 50)  |

✅ Are Both InBuildDBCP and ContextListener Compulsory?
No, you don't need both.
It depends on how you want to manage your database connections.
🎯 Option 1: Use ContextListener with Apache DBCP (Manual Pooling)

You manage your own connection pool using BasicDataSource in ContextListener:--->
@WebListener
public class ContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        BasicDataSource ds = new BasicDataSource();
        // configure db details
        sce.getServletContext().setAttribute("dataSource", ds);
    }
}

Then in your Servlet, get it like this:--->
BasicDataSource ds = (BasicDataSource) getServletContext().getAttribute("dataSource");
Connection con = ds.getConnection();

✅ This is custom pooling, and you control settings like size, URL, etc.

🎯 Option 2: Use @Resource Injection (Inbuilt DBCP / App Server pooling)

Your servlet directly uses :--->
@Resource(name = "java:comp/env/jdbc/pool")
private DataSource dataSource;

Then use dataSource.getConnection().

✅ This is container-managed pooling. You don’t write pooling code — it's set up in context.xml or server config.

🔍 So… which one should you use?
| Case                                              | Use `ContextListener` | Use `@Resource` (`InBuildDBCP`) |
| ------------------------------------------------- | --------------------- | ------------------------------- |
| You want full control of pooling                  | ✅ Yes                | ❌ Not needed                   |
| You want to keep it simple                        | ❌ Not needed         | ✅ Yes                          |
| Your server manages connection pool               | ❌ Not needed         | ✅ Yes                          |
| You don’t have `context.xml` or app server config | ✅ Yes                | ❌ Won’t work                   |

🟢 Final Answer:
❗ Use either one, not both.

If you use ContextListener, don’t use @Resource injection.
If you use @Resource injection, don’t write BasicDataSource in ContextListener.

✅ How they work together
At server startup:
    -ContextListener.contextInitialized() runs.
    -Inside it, we create a DBCP connection pool using BasicDataSource.
    -We save it to ServletContext (shared memory).

Other servlets can access it easily using:--->
BasicDataSource ds = (BasicDataSource) getServletContext().getAttribute("dataSource");

✅ In short:
| Concept           | Purpose                                                                                  |
| ----------------- | ---------------------------------------------------------------------------------------- |
| `BasicDataSource` | Manages a pool of DB connections (DBCP)                                                  |
| `ContextListener` | Creates the DB pool at startup and shares it for whole app                               |
| Why together?     | So all servlets can use 1 efficient shared DB pool instead of slow, separate connections |

*/
