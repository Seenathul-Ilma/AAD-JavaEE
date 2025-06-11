package lk.ijse.gdse71;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.SQLException;

/**
 * --------------------------------------------
 * Author: Zeenathul Ilma
 * GitHub: https://github.com/Seenathul-Ilma
 * Website: https://zeenathulilma.vercel.app/
 * --------------------------------------------
 * Created: 6/6/2025 10:11 AM
 * Project: Examples
 * --------------------------------------------
 **/

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //System.out.println("contextInitialized");
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/eventdb");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("1234");
        basicDataSource.setInitialSize(50);
        basicDataSource.setMaxTotal(100);

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("dataSource", basicDataSource);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //System.out.println("contextDestroyed");
        try {
            ServletContext servletContext = sce.getServletContext();
            BasicDataSource basicDataSource = (BasicDataSource) servletContext.getAttribute("dataSource");
            basicDataSource.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


/*
💡 Why Use ContextListener?
ContextListener runs automatically when the server starts.
We use it to initialize things once at startup — like:
 -DB connection pool (BasicDataSource)
 -Config files
 -Any shared resource

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
|  Why together?    | So all servlets can use 1 efficient shared DB pool instead of slow, separate connections |

*/
