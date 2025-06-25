package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 */
public class JDBCConnection {

    // Name of database file (contained in database folder)
    public static final String DATABASE = "jdbc:sqlite:database/vicfreewifi20ap20map20data2020170724.db";
    
    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    
    public static void Locations()  {        
        Scanner fileScanner;
        try {
            fileScanner = new Scanner(new File("vicfreewifi20ap20map20data2020170724.csv")); // Change to suit your file
            try (Connection connection = DriverManager.getConnection(JDBCConnection.DATABASE)) {
                fileScanner.nextLine();
                while(fileScanner.hasNextLine()){
                    try (Statement statement = connection.createStatement()) {
                        statement.setQueryTimeout(30);
                        String insertQuery = "INSERT INTO Location (name, LongName, latitude, longitude) VALUES (";
                        String[] fields=fileScanner.nextLine().split(",");
                        insertQuery += "'" + fields[0] + "',";      // name
                        insertQuery += "'" + fields[1] + "',";      // long name
                        insertQuery += "'" + fields[2] + "',";      // latitude
                        insertQuery += "'" + fields[3] + "');";     // longitude
                        //insertQuery+=fields[4]+");";     
        
                        System.out.println("About to execute query: "+insertQuery);
                        statement.executeUpdate(insertQuery);
                    }
                }
            }
            fileScanner.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.err.println("Check your code and CSV data.");
        } catch (FileNotFoundException e) {
            System.err.println("CSV file not found: " + e.getMessage());
        }
    }
    public static void stat()  {        
        Scanner fileScanner;
        try {
            fileScanner = new Scanner(new File("vicfreewifi20ap20map20data2020170724.csv")); // Change to suit your file
            try (Connection connection = DriverManager.getConnection(JDBCConnection.DATABASE)) {
                fileScanner.nextLine();
                while(fileScanner.hasNextLine()){
                    try (Statement statement = connection.createStatement()) {
                        statement.setQueryTimeout(30);
                        String insertQuery = "INSERT INTO Status (name,status) VALUES (";
                        String[] fields=fileScanner.nextLine().split(",");
                        insertQuery += "'" + fields[0] + "',";      // name
                        insertQuery += "'" + fields[5] + "');";      //status
                        // insertQuery += "'" + fields[2] + "',";      // latitude
                        // insertQuery += "'" + fields[3] + "');";     // longitude
                        //insertQuery+=fields[4]+");";     
        
                        System.out.println("About to execute query: "+insertQuery);
                        statement.executeUpdate(insertQuery);
                    }
                }
            }
            fileScanner.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.err.println("Check your code and CSV data.");
        } catch (FileNotFoundException e) {
            System.err.println("CSV file not found: " + e.getMessage());
        }
    }
        public static void type()  {        
            Scanner fileScanner;
            try {
                fileScanner = new Scanner(new File("vicfreewifi20ap20map20data2020170724.csv")); // Change to suit your file
                try (Connection connection = DriverManager.getConnection(JDBCConnection.DATABASE)) {
                    fileScanner.nextLine();
                    while(fileScanner.hasNextLine()){
                        try (Statement statement = connection.createStatement()) {
                            statement.setQueryTimeout(30);
                            String insertQuery = "INSERT INTO KIND(Name,Kind) VALUES (";
                            String[] fields=fileScanner.nextLine().split(",");
                            insertQuery += "'" + fields[0] + "',";      // name
                            insertQuery += "'" + fields[4] + "');";      //status
                            // insertQuery += "'" + fields[2] + "',";      // latitude
                            // insertQuery += "'" + fields[3] + "');";     // longitude
                            //insertQuery+=fields[4]+");";     
            
                            System.out.println("About to execute query: "+insertQuery);
                            statement.executeUpdate(insertQuery);
                        }
                    }
                }
                fileScanner.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                System.err.println("Check your code and CSV data.");
            } catch (FileNotFoundException e) {
                System.err.println("CSV file not found: " + e.getMessage());
            }
        }
 
 
}