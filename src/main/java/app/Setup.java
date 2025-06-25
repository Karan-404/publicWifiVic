package app;

import io.javalin.http.Context;
import io.javalin.http.Handler;


public class Setup implements Handler {

    // URL of this page relative to http://localhost:7002/
    public static final String URL = "/setup";

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

        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """
            <div class='topnav'>
                <a href='/'>Public Wi-Fi Homepage</a>
                <a href='/location'> Wi-Fi Locations</a>
                <a href='/type'> Wi-Fi Types</a>
                <a href='/status1'>Wi-Fi status</a>
                <a href='/setup'>Setup and Safety</a>
            </div>
        """;

        // Add header content block
        html = html + """
            <div class='header'>
                <h1>
                <img src='wifilogo.png' class='top-image' alt='WiFi logo' height='75'>
                <span class='gradient-text'>Public WiFi Melbourne Setup & Safety</span>
                </h1>
                <p class='header-subtitle'>Learn how to safely connect to public Wi-Fi networks</p>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";
          
          html = html +" <b><p>Hello Melbourne!</p></b>";
          
        // Add HTML for the list of pages (as well as topnav)
        html = html + """
            <h2>How to access FreeWiFi in Melbourne </h2>
            <p> Look up available networks on your device</p>
            <p>Select 'PublicFreeWiFi'</p>
            <p>Accept the terms and conditions</p>

           <h2> <p>Using public wi-fi networks safely</p></h2>
            <p>No public wi-fi is totally secure. Here are some ways to stay safe when accessing any free public wi-fi network.
            If your wi-fi connection is not encrypted, others using wi-fi in your area may be able to monitor information passing between your device and the network.</p>

            <p>* Staying safe on public wi-fi</p>
            <p>* Avoid sensitive transactions like banking and online shopping</p>
            <p>* Maintain anti-virus protection</p>
            <p>* Install a trusted VPN app</p>
            <p>* Keep software updated</p>
            <p>* Have strong passwords
            </p>
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
