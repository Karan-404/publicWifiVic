# VicFreeWiFi Web Application

## Project Overview
This project is a Java web application for visualizing and managing public WiFi locations in Victoria, Australia. It uses a SQLite database and a lightweight web server to provide a user-friendly interface for exploring WiFi access points.

## Folder Structure
```bash
├── /src/main                    - Location of all files as required by build configuration
│         ├── java               - Java Source location
│         │    └── app           - package location for all Java files
│         └── resources          - Web resources (html templates / style sheets)
│               ├── css          - CSS Style-sheets
│               └── images       - Image files
│ 
├── /target                      - build directory (DO NOT MODIFY)
├── /database                    - The folder to store sqlite database files (*.db files)
├── pom.xml                      - Configure Build (DO NOT MODIFY)
└── README.md                    - This file ;)
```

## Key Libraries & Dependencies
- **org.xerial.sqlite-jdbc**: SQLite JDBC driver for database connectivity
- **javalin**: Lightweight Java web server
- **slf4j-simple**: Logging (required by Javalin)

All dependencies are managed via Maven in `pom.xml`.

## Database
- The SQLite database file is located in the `/database` folder.
- Example: `vicfreewifi20ap20map20data2020170724.db`
- You can use tools like DB Browser for SQLite to inspect or modify the database.

## How to Build & Run
1. **Open the project in VS Code**
2. Let VS Code detect and configure the Maven project (accept prompts to download dependencies)
3. **Build & Run:**
   - Open `src/main/java/app/App.java`
   - Click "Run" above the `main` method, or use the VS Code Run menu
4. **Access the app:**
   - Open your browser and go to: [http://localhost:7002](http://localhost:7002)

## Features
- View a map of public WiFi locations
- Browse and search WiFi access points
- Responsive web interface (HTML/CSS)
- Data stored in a local SQLite database

## Resources
- `src/main/resources/firstpage.html`: Main HTML template
- `src/main/resources/css/common.css`: Common styles
- `src/main/resources/images/`: Project images and logos



---

© 2025 VicFreeWiFi Project Team




