package app;

import io.javalin.http.Context;
import io.javalin.http.Handler;


public class PageIndexKS implements Handler {

    // URL of this page relative to http://localhost:7002/
    public static final String URL = "/project";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Header information
        html = html + "<head>" + 
               "<title>Homepage</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add thesidequest.com header
        html = html + "<div style='text-align:center; font-size:2rem; font-weight:bold; margin: 1.5rem 0 1rem 0;'><a href='/' style='color:inherit; text-decoration:none;'>thesidequest.com</a></div>";

        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """
            <div class='topnav'>
                <a href='/project'>Project Homepage</a>
                <a href='/location'>WiFi Locations</a>
                <a href='/type'>Connection Types</a>
                <a href='/status1'>Network Status</a>
                <a href='/setup'>Setup & Safety</a>
            </div>
        """;

        // Add header content block
        html = html + """
            <div class='header'>
                <h1>
                <img src='wifilogo.png' class='top-image' alt='WiFi logo' height='75'>
                <span class='gradient-text'>Public WiFi Melbourne</span>
                </h1>
                <p class='header-subtitle'>Your gateway to Melbourne's connected city</p>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";
		  
        html = html + """
            <div class='text-center mb-2'>
                <h2 class='welcome-title'>Hello Melbourne!</h2>
                <p class='welcome-text'>
                    Welcome to Melbourne's Public WiFi Hub! Discover the city's digital infrastructure with our comprehensive 
                    WiFi network explorer. Find hotspots, check connection types, and stay informed about network trends. 
                    Join us as we navigate Melbourne's connected landscape together!
                </p>
            </div>
            
            <div class='cards-grid'>
        """;

        // Add HTML for the list of pages with card design
        html = html + """
                <div class='card'>
                    <h3 class='card-title'>Setup & Safety</h3>
                    <p class='card-text'>Learn how to safely connect to public WiFi networks with our comprehensive security guide.</p>
                    <a href='/setup' class='btn'>Get Started</a>
                </div>
                
                <div class='card'>
                    <h3 class='card-title'>WiFi Locations</h3>
                    <p class='card-text'>Explore all WiFi hotspots across Melbourne with detailed coordinates and coverage information.</p>
                    <a href='/location' class='btn'>Find Locations</a>
                </div>
                
                <div class='card'>
                    <h3 class='card-title'>Connection Types</h3>
                    <p class='card-text'>Discover different types of WiFi connections and their unique characteristics.</p>
                    <a href='/type' class='btn'>View Types</a>
                </div>
                
                <div class='card'>
                    <h3 class='card-title'>Network Status</h3>
                    <p class='card-text'>Check real-time status of WiFi networks - see what's active and what's coming soon.</p>
                    <a href='/status1' class='btn'>Check Status</a>
                </div>
            </div>
        """;


        // Close Content div
        html = html + "</div>";

        // Footer
        html = html + """
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
        html = html + "</body>" + "</html>";


        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
