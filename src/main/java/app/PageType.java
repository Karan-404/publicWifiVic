package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;


public class PageType implements Handler {

    // URL of this page relative to http://localhost:7002/
    public static final String URL = "/type";

    @Override
public void handle(Context context) throws Exception {
    // Create a simple HTML webpage in a String
    String html = "<html>";

    // Add some Head information
    html += "<head>" + 
           "<title>Locations</title>" +
           "<link rel='stylesheet' type='text/css' href='common.css' />" +
           "</head>";

    // Add the body
    html += "<body>";

    // Add the topnav
    html += """
        <div class='topnav'>
            <a href='/'>Public Wi-Fi Hub</a>
            <a href='/location'>WiFi Locations</a>
            <a href='/type'>Connection Types</a>
            <a href='/status1'>Network Status</a>
            <a href='/setup'>Setup & Safety</a>
        </div>
    """;

    // Add header content block
    html += """
        <div class='header'>
            
            <h1>
                <img src='wifilogo.png' class='top-image' alt='WiFi logo' height='75'>
                <span class='gradient-text'>Public Wi-Fi Melbourne Connection Types</span>
                </h1>
            <p class='header-subtitle'>Explore different types of Wi-Fi connections available</p>
        </div>
    """;

    // Add Div for page Content
    html += "<div class='content'>";
    
    ArrayList<String> statusCounts = getTypeCounts();
    html += "<div class='table-header'>";
    html += "<h2 class='table-title'>WiFi Connection Types</h2>";
    html += "<span class='table-count'>" + statusCounts.size() + " types available</span>";
    html += "</div>";
    
    html += "<div class='table-container'>";
    html += "<table class='modern-table'>";
    html += "<thead><tr><th>Connection Type</th><th>Number of Locations</th></tr></thead>";
    html += "<tbody>";

    for (String statusCount : statusCounts) {
        String[] parts = statusCount.split("  ");
        if (parts.length >= 2) {
            String type = parts[0].substring(7); // Remove "Type:  " prefix
            String count = parts[1].substring(7); // Remove "Count: " prefix
            html += "<tr><td>" + type + "</td><td>" + count + "</td></tr>";
        }
    }

    html += "</tbody></table>";
    html += "</div>"; // Close table-container

    // Add some spacing between tables
    html += "<div style='margin: 3rem 0;'></div>";

    // Add HTML for the locations table
    html += "<div class='table-header'>";
    html += "<h2 class='table-title'>All WiFi Locations by Type</h2>";
    html += "</div>";
    
    html += "<div class='table-container'>";
    html += "<table class='modern-table'>";
    html += "<thead><tr><th>Location Name</th><th>Connection Type</th><th>Full Address</th></tr></thead>";
    html += "<tbody>";

    ArrayList<String> locationRows = getLocations();
    for (String locationRow : locationRows) {
        html += locationRow;
    }

    html += "</tbody></table>";
    html += "</div>"; // Close table-container

    // Close Content div
    html += "</div>";

    // Footer
    html += """
        <div class='footer'>
            <div class='footer-content'>
                <div class='footer-section'>
                    <p class='footer-heading'>Contact Information</p>
                    <p class='footer-text'>Email: ping@thesubquest.com</p>
                </div>
            </div>
            <div class='footer-bottom'>
                <p class='footer-copyright'>© 2023 Melbourne Public WiFi Hub. All rights reserved.</p>
            </div>
        </div>
    """;

    // Finish the HTML webpage
    html += "</body>" + "</html>";
    

    // DO NOT MODIFY THIS
    // Makes Javalin render the webpage
    context.html(html);
}




   
    public ArrayList<String> getLocations() {
            ArrayList<String> locations = new ArrayList<>();
    
            try (Connection connection = DriverManager.getConnection(JDBCConnection.DATABASE);
                 Statement statement = connection.createStatement()) {
                statement.setQueryTimeout(30);
                // SQLite does not support FULL OUTER JOIN, use LEFT JOIN instead
                ResultSet results = statement.executeQuery("SELECT k.Name, k.Kind, l.LongName FROM Kind k LEFT JOIN Location l ON k.Name = l.Name");
    
                while (results.next()) {
                    String name = results.getString("Name");
                    String kind = results.getString("Kind");
                    String longName = results.getString("LongName");
                    // String latitude = results.getString("latitude");
                    locations.add("<tr><td>" + name + "</td><td>" + kind + "</td><td>" + longName + "</td></tr>");
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
    
            return locations;
        }
    
        public ArrayList<String> getTypeCounts() {
            ArrayList<String> statusCounts = new ArrayList<>();
    
            try (Connection connection = DriverManager.getConnection(JDBCConnection.DATABASE);
                 Statement statement = connection.createStatement()) {
                statement.setQueryTimeout(30);
                
                // SQL query to get Wi-Fi status counts
                String query = "SELECT UPPER(Kind) AS Kind, COUNT(*) AS count_of_Types FROM KIND GROUP BY Kind COLLATE NOCASE";
                    
                ResultSet results = statement.executeQuery(query);
    
                while (results.next()) {
                    String Kind = results.getString("Kind");
                    int count = results.getInt("count_of_Types");
                    statusCounts.add("TYPES: " + Kind + "  Count: " + count);
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
    
            return statusCounts;
        }
}