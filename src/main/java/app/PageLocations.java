package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class PageLocations implements Handler {
 // URL of this page relative to http://localhost:7002/
 public static final String URL = "/location";

 @Override
 public void handle(Context context) throws Exception {
     String html = "<html>";
 
     html += "<head>" + 
            "<title>Locations</title>" +
            "<link rel='stylesheet' type='text/css' href='common.css' />" +
            "<link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.9.4/dist/leaflet.css\" />" +
            "<script src=\"https://unpkg.com/leaflet@1.9.4/dist/leaflet.js\"></script>" +
            "</head>";
 
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
 
     html += """
         <div class='header'>
             <h1>
                 <img src='wifilogo.png' class='top-image' alt='WiFi logo' height='75'>
                 <span class='gradient-text'>Public WiFi Locations</span>
             </h1>
             <p class='header-subtitle'>Discover all WiFi hotspots across Melbourne</p>
         </div>
     """;
 
     html += "<div class='content'>";

     // Add map container
     html += "<div id='map' style='height: 500px; width: 100%; margin-bottom: 2rem; border: 1px solid #ccc; border-radius: 8px;'></div>";

     // Add form for searching by Long Name
     html += """
         <div class='search-form'>
             <form action="/location" method="get">
                 <label for="searchLongName">Search by Address or Location Name:</label>
                 <input type="text" id="searchLongName" name="searchLongName" placeholder="Enter address or location name...">
                 <input type="submit" value="Search Locations">
             </form>
         </div>
     """;

     ArrayList<String> locations = getLocations(context.queryParam("searchLongName"));

     // Prepare JavaScript array of location objects
     StringBuilder jsLocations = new StringBuilder();
     jsLocations.append("[");
     boolean firstLocation = true;
     for (String location : locations) {
         String[] parts = location.split(" - Address: ");
         if (parts.length >= 2) {
             String name = parts[0].replace("\"", "\\\"").replace("'", "\\'");
             String[] details = parts[1].split(" \\(");
             if (details.length >= 2) {
                 String address = details[0].replace("\"", "\\\"").replace("'", "\\'");
                 String[] coordinates = details[1].split(", ");
                 if (coordinates.length >= 2) {
                     try {
                         String lat = coordinates[0].trim();
                         String lon = coordinates[1].substring(0, coordinates[1].length() - 1).trim();
                         
                         // Validate coordinates are valid numbers
                         if (lat.matches("-?\\d+(\\.\\d+)?") && lon.matches("-?\\d+(\\.\\d+)?")) {
                             if (!firstLocation) {
                                 jsLocations.append(",");
                             }
                             jsLocations.append("{name: \"").append(name).append("\", address: \"").append(address).append("\", lat: ").append(lat).append(", lon: ").append(lon).append("}");
                             firstLocation = false;
                         }
                     } catch (Exception e) {
                         System.err.println("Error processing coordinates for location: " + name);
                     }
                 }
             }
         }
     }
     jsLocations.append("]");

     html += "<script>";
     html += "document.addEventListener('DOMContentLoaded', function() {";
     html += "if (typeof L === 'undefined') {";
     html += "document.getElementById('map').innerHTML = '<p style=\"text-align: center; padding: 20px;\">Map library failed to load.</p>';";
     html += "return;";
     html += "}";
     html += "var map = L.map('map').setView([-37.8136, 144.9631], 12);";
     html += "L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {";
     html += "maxZoom: 19, attribution: '© OpenStreetMap contributors'}).addTo(map);";
     html += "var locations = " + jsLocations.toString() + ";";
     html += "if (locations.length > 0) {";
     html += "locations.forEach(function(loc) {";
     html += "if (loc.lat && loc.lon) {";
     html += "var marker = L.marker([parseFloat(loc.lat), parseFloat(loc.lon)]).addTo(map);";
     html += "marker.bindPopup('<b>' + loc.name + '</b><br/>' + loc.address);";
     html += "}";
     html += "});";
     html += "} else {";
     html += "document.getElementById('map').innerHTML = '<p style=\"text-align: center; padding: 20px;\">No WiFi locations found.</p>';";
     html += "}";
     html += "});";
     html += "</script>";

     html += "<div class='table-header'>";
     html += "<h2 class='table-title'>WiFi Locations</h2>";
     html += "<span class='table-count'>" + locations.size() + " locations found</span>";
     html += "</div>";
     
     html += "<div class='table-container'>";
     html += "<table class='modern-table'>";
     html += "<thead><tr><th>Location Name</th><th>Address</th><th>Latitude</th><th>Longitude</th></tr></thead>";
     html += "<tbody>";

     for (String location : locations) {
         String[] parts = location.split(" - Address: ");
         if (parts.length >= 2) {
             String[] details = parts[1].split(" \\(");
             if (details.length >= 2) {
                 String[] coordinates = details[1].split(", ");
                 if (coordinates.length >= 2) {
                     html += "<tr><td>" + parts[0] + "</td><td>" + details[0] + "</td><td>" + coordinates[0] + "</td><td>" + coordinates[1].substring(0, coordinates[1].length() - 1) + "</td></tr>";
                 }
             }
         }
     }

     html += "</tbody></table>";
     html += "</div>"; // Close table-container

     html += "</div>"; // Close content
 
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
 
     html += "</body>" + "</html>";
     
 
     context.html(html);
 }
 
 


    public ArrayList<String> getLocations(String searchLongName) {
        ArrayList<String> locations = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBCConnection.DATABASE);
             Statement statement = connection.createStatement()) {
            
            statement.setQueryTimeout(30);
            
            String query = "SELECT * FROM Location ";
            if (searchLongName != null && !searchLongName.isEmpty()) {
                query += " WHERE LongName LIKE '%" + searchLongName + "%'";
            }
            
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String name = results.getString("name");
                String LongName = results.getString("LongName");
                String longitude = results.getString("longitude");
                String latitude = results.getString("latitude");
                locations.add(name + " - Address: " + LongName + " (" + latitude + ", " + longitude + ")");
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return locations;
    }
}
