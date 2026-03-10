# VicFreeWiFi Web Application

## Project Overview
This project is a static website for visualizing public WiFi locations in Victoria, Australia. It is hosted on **GitHub Pages** and uses pre-exported WiFi data (sourced from a SQLite database) to provide a user-friendly interface for exploring WiFi access points without requiring a backend server.

Try it out at: **https://karan-404.github.io/publicWifiVic/**

## Folder Structure
```
docs/
  index.html        - Home page
  location.html     - WiFi Locations page (with interactive map)
  type.html         - Connection Types page
  status1.html      - Network Status page
  setup.html        - Setup & Safety page
  css/              - CSS Style-sheets
  images/           - Image files (logo etc.)
  data/             - Pre-exported WiFi data (wifi-data.js)

src/                - Original Java backend source (legacy, not deployed)
database/           - SQLite database (source of the WiFi data)
.github/workflows/static.yml  - GitHub Actions workflow for Pages deployment
pom.xml             - Maven build config for Java backend (legacy)
README.md           - This file ;)
```

## Deployment
The site is deployed automatically to **GitHub Pages** via GitHub Actions whenever changes are pushed to the `main` branch. The workflow deploys the `docs/` folder as the static site.

## Features
- View an interactive map of public WiFi locations (powered by Leaflet.js + OpenStreetMap)
- Browse and search WiFi access points by name or address
- Filter networks by connection type or status
- Responsive web interface (HTML/CSS/JavaScript)
- All 517 WiFi access point records embedded as static data (no backend required)

## Resources
- `docs/index.html`: Home page
- `docs/location.html`: Locations page with interactive map and search
- `docs/type.html`: Connection types page
- `docs/status1.html`: Network status page
- `docs/setup.html`: Setup & Safety page
- `docs/css/common.css`: Common styles
- `docs/images/`: Project images and logos
- `docs/data/wifi-data.js`: Pre-exported WiFi data from the SQLite database

---

© 2025 VicFreeWiFi Project Team
