# EventCrafter

EventCrafter is a web-based event registration and management system built using Jakarta EE, JSP/Servlets, and JDBC. It allows users to create, manage, and register for events while handling authentication, session management, and database integration. This project is designed as a professional portfolio piece to showcase Java enterprise development skills and deployment on Tomcat.

## Features

- **Dynamic Web Content** – Uses JSP and Servlets to generate pages based on user interactions.
- **User Authentication & Authorization** – Secure login system with role-based access.
- **Session Management** – Tracks logged-in users and their activity.
- **Database Integration** – Stores and retrieves event data using JDBC.
- **Form Handling & Validation** – Ensures accurate data input for event creation and registration.
- **Error Handling** – Provides robust error-catching mechanisms.
- **Responsive Design** – Uses Bootstrap for a modern and mobile-friendly UI.
- **Deployment to Tomcat** – Hosted on an AWS cloud environment for real-world scalability.

## Future Enhancements

- **API Integration** – Connect with external APIs for features like event location mapping.
- **Payment Processing** – Implement a system for paid event registrations.
- **Spring Boot Upgrade** – Transition to Spring Boot for improved development efficiency and scalability.

## Tech Stack

- Backend: Jakarta EE (JSP, Servlets), JDBC, Tomcat
- Frontend: HTML, CSS, Bootstrap, JavaScript
- Database: MySQL
- Deployment: Tomcat

## Getting Started

### Prerequisites

- Java 17+
- Apache Tomcat 10+
- MySQL
- Maven for dependency management

### Installation

1. Clone the repository

```bash
git clone https://github.com/programmingwithtyler/EventCrafter.git
```

2. Configure the database connection in `DatabaseConnection.java`
3. build the project using Maven:

```bash
mvn clean install
```

4. Deploy the WAR file to Apache Tomcat.
5. Access the app at `http://localhost:8080/EventCrafter\`
