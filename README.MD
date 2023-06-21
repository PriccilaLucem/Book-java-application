# Simple project using java spring boot app

This project is a simple Java Spring Boot application that focuses on user management, authentication, email verification, and database operations. It utilizes various dependencies to achieve these functionalities.

The project includes the following dependencies:

- Spring Boot Starter Email: Provides support for sending emails using the JavaMailSender interface.
- Spring Security Config: Enables the configuration of Spring Security for securing the application.
- Spring Boot Starter Security: Provides basic security features for the Spring Boot application.
- JSON Web Token (JWT): Enables the generation and verification of JSON Web Tokens for authentication and authorization purposes.
- ModelMapper: Facilitates object mapping between DTOs (Data Transfer Objects) and entities.
JBCrypt: A library for hashing passwords using the BCrypt hashing algorithm.
 - The functionalities of the project include:

- User Creation: Allows the creation of users, including password hashing using JBCrypt.
- Email Verification: Generates a validation table and sends an email with a link to verify the user's email address.
- Route Blocking: Blocks certain routes depending on the token provided, enforcing access control.
- Book Management: Enables the creation of books and establishes a many-to-many relationship with users.
- Database Migrations: Utilizes migrations to update the database schema in a controlled and versioned manner.
- .env File: Uses a .env file to store sensitive variables that need to be changed, providing a secure way to manage configuration settings.

# Not ready yet

Missing dependencies

- Swagger documentation
- Books routes and entity
- Front-end application
