package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class Status implements Handler {

    // URL of this page relative to http://localhost:7002/
    public static final String URL = "/status1";

    @Override
public void handle(Context context) throws Exception {
    // Create a simple HTML webpage in a String
    String html = "<html>";

    // Add some Head information
    html += "<head>" + "<title>Locations</title>";

    // Add some CSS (external file)
    html += "<link rel='stylesheet' type='text/css' href='common.css' />";
    html += "</head>";

    // Add the body
    html += "<body>";
    html += """
        <div class='topnav'>
            <a href='/'>Public Wi-Fi Hub</a>
            <a href='/location'>WiFi Locations</a>
            <a href='/type'>Connection Types</a>
            <a href='/status1'>Network Status</a>
            <a href='/setup'>Setup & Safety</a>
        </div>
    """;
    html = html + """
        <div class='header'>
            
            <h1>
                <img src='wifilogo.png' class='top-image' alt='WiFi logo' height='75'>
                <span class='gradient-text'>Public WiFi Melbourne Network Status</span>
                </h1>
            <p class='header-subtitle'>Real-time Wi-Fi network status across Melbourne</p>
        </div>
    """;



    // Add Div for page Content
    html += "<div class='content'>";
    
    // Add status overview section
    ArrayList<String> statusCounts = getStatusCounts();
    html += "<div class='table-header'>";
    html += "<h2 class='table-title'>Network Status Overview</h2>";
    html += "<span class='table-count'>" + statusCounts.size() + " status types</span>";
    html += "</div>";
    
    html += "<div class='cards-grid' style='grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); margin-bottom: 3rem;'>";
    
    for (String statusCount : statusCounts) {
        String[] parts = statusCount.split(" :- ");
        if (parts.length >= 2) {
            String status = parts[0];
            String count = parts[1];
            String statusClass = status.equals("UP") ? "card-success" : "card-warning";
            html += "<div class='card " + statusClass + "'>";
            html += "<h3 class='card-title'>" + status + " Networks</h3>";
            html += "<p class='card-text'>Currently " + count + " networks are " + status.toLowerCase() + "</p>";
            html += "</div>";
        }
    }
    html += "</div>";

    // Add filter form
    html += """
        <div class='search-form'>
            <form action="/status1" method="get">
                <label for="status">Filter networks by status:</label>
                <select id="status" name="status" style="padding: 0.75rem; border: 2px solid #e5e7eb; border-radius: 8px; font-size: 1rem; margin-bottom: 1rem;">
                    <option value="">Show All Networks</option>
                    <option value="UP">Active Networks (UP)</option>
                    <option value="FUTURE">Future Networks</option>
                </select>
                <input type="submit" value="Apply Filter">
            </form>
        </div>
    """;

    // Get filtered WiFi status data
    ArrayList<String> wifiStatus = getStatus(context.queryParam("status"));
    
    html += "<div class='table-header'>";
    html += "<h2 class='table-title'>Network Status Details</h2>";
    html += "<span class='table-count'>" + wifiStatus.size() + " networks found</span>";
    html += "</div>";
    
    html += "<div class='table-container'>";
    html += "<table class='modern-table'>";
    html += "<thead><tr><th>Network Name</th><th>Status</th></tr></thead>";
    html += "<tbody>";

    // Populate the table with WiFi status data
    for (String status : wifiStatus) {
        String[] parts = status.split(" - ");
        if (parts.length >= 2) {
            String networkName = parts[0];
            String networkStatus = parts[1];
            String statusBadge = networkStatus.equals("UP") ? 
                "<span style='background: #22c55e; color: white; padding: 0.25rem 0.75rem; border-radius: 20px; font-size: 0.85rem; font-weight: 600;'>Active</span>" :
                "<span style='background: #f59e0b; color: white; padding: 0.25rem 0.75rem; border-radius: 20px; font-size: 0.85rem; font-weight: 600;'>Future</span>";
            html += "<tr><td>" + networkName + "</td><td>" + statusBadge + "</td></tr>";
        }
    }

    // Close the table and containers
    html += "</tbody></table>";
    html += "</div>"; // Close table-container
    html += "</div>"; // Close content

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

    // Makes Javalin render the webpage
    context.html(html);
}


    /**
     * Get all of the WiFi Status in the database
     * 
     * @return Returns an ArrayList of String with ONLY the WiFi status
     */
    public ArrayList<String> getStatus(String filter) {
        ArrayList<String> wifiStatus = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBCConnection.DATABASE);
             Statement statement = connection.createStatement()) {
            statement.setQueryTimeout(30);
            // SQLite does not support FULL OUTER JOIN, use LEFT JOIN instead
            String query = "SELECT s.Name, s.Status, l.LongName FROM status s LEFT JOIN Location l ON s.Name = l.Name ";
            if (filter != null && !filter.isEmpty()) {
                query += " WHERE s.Status = ?";
            }
            var pstmt = connection.prepareStatement(query);
            if (filter != null && !filter.isEmpty()) {
                pstmt.setString(1, filter);
            }
            ResultSet results = pstmt.executeQuery();

            while (results.next()) {
                String name = results.getString("Name");
                String status = results.getString("Status");
                //String LongName = results.getString("LongName");
                wifiStatus.add(name + " - " + status); //+LongName);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return wifiStatus;
    }
    public ArrayList<String> getStatusCounts() {
        ArrayList<String> statusCounts = new ArrayList<>();
    
        try (Connection connection = DriverManager.getConnection(JDBCConnection.DATABASE);
             Statement statement = connection.createStatement()) {
            statement.setQueryTimeout(30);
            
            // SQL query to get Wi-Fi status counts
            String query = "SELECT Status, COUNT(*) AS count_of_status FROM status GROUP BY Status COLLATE NOCASE";
            
            ResultSet results = statement.executeQuery(query);
    
            while (results.next()) {
                String status = results.getString("Status");
                int count = results.getInt("count_of_status");
                statusCounts.add(  status + " :- " + count);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    
        return statusCounts;
    }
    
}
