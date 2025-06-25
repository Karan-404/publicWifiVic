package app;

import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;

/**
 * Main Application Class.
 * <p>
 * Running this class as regular java application will start the 
 * Javalin HTTP Server and our web application.
 *
 */
public class App {

    public static final int         JAVALIN_PORT    = 7002;
    public static final String      CSS_DIR         = "css/";
    public static final String      IMAGES_DIR      = "images/";

    public static void main(String[] args) {
        // Create our HTTP server and listen in port 7002
        Javalin app = Javalin.create(config -> {
            config.registerPlugin(new RouteOverviewPlugin("/help/routes"));
            
            // Uncomment this if you have files in the CSS Directory
            config.addStaticFiles(CSS_DIR);

            // Uncomment this if you have files in the Images Directory
            config.addStaticFiles(IMAGES_DIR);
        }).start(JAVALIN_PORT);
        //task2();
        //task3(); 

        // Configure Web Routes
        configureRoutes(app);
        
        // Initialize database connection (commented out method calls can be enabled as needed)
        // JDBCConnection jdbc = new JDBCConnection();
        // jdbc.locations();
        // jdbc.stat();
        // jdbc.type();
    }

    public static void configureRoutes(Javalin app) {
        // Remove duplicate root route registration to avoid conflict
        // app.get(HomePage.URL, new HomePage());
        app.get(PageIndexKS.URL, new PageIndexKS());
        app.get(PageLocations.URL, new PageLocations());
        app.get(Status.URL, new Status());
        app.get(Setup.URL, new Setup());
        app.get(PageType.URL, new PageType());
    }

}
