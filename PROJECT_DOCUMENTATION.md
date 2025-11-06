# Project Documentation

## Project Overview

This project is a Spring Boot application that provides a comprehensive portfolio management system. It allows users to manage their professional information, including their profile, skills, projects, education, and work experience. The application also includes features for tracking website analytics and managing contact inquiries.

## Technologies Used

*   **Backend:**
    *   Java 21
    *   Spring Boot 3.5.7
    *   Spring Web
    *   Spring Data JPA
    *   Spring Security
*   **Database:**
    *   MySQL (mysql-connector-j 9.4.0)
*   **API Documentation:**
    *   SpringDoc OpenAPI (springdoc-openapi-starter-webmvc-ui 2.2.0)
*   **Build Tool:**
    *   Maven

## API Endpoints

### Authentication

*   `POST /api/auth/login`: Authenticates a user and returns a JWT token.
*   `POST /api/auth/register`: Registers a new user.
*   `POST /api/auth/logout`: Logs out the current user.
*   `GET /api/auth/me`: Retrieves the profile of the currently authenticated user.

### Users

*   `GET /api/users/profile`: Retrieves the profile of the currently authenticated user. (Admin only)
*   `GET /api/users/profile/details`: Retrieves the profile of the currently authenticated user with detailed information. (Admin only)
*   `PUT /api/users/profile`: Updates the profile of the currently authenticated user. (Admin only)
*   `GET /api/users/{userId}`: Retrieves the profile of a specific user by their ID. (Admin only)

### Skills

*   `GET /api/skills`: Retrieves all skills for the currently authenticated user. (Admin only)
*   `GET /api/skills/featured`: Retrieves the featured skills for the currently authenticated user. (Admin only)
*   `GET /api/skills/category/{category}`: Retrieves skills by category for the currently authenticated user. (Admin only)
*   `GET /api/skills/{skillId}`: Retrieves a specific skill by its ID. (Admin only)
*   `POST /api/skills`: Creates a new skill for the currently authenticated user. (Admin only)
*   `PUT /api/skills/{skillId}`: Updates a specific skill. (Admin only)
*   `DELETE /api/skills/{skillId}`: Deletes a specific skill. (Admin only)
*   `GET /api/skills/user/{userId}`: Retrieves all skills for a specific user. (Admin only)
*   `GET /api/skills/user/{userId}/featured`: Retrieves the featured skills for a specific user. (Admin only)
*   `GET /api/skills/user/{userId}/category/{category}`: Retrieves skills by category for a specific user. (Admin only)

### Contacts

*   `POST /api/contacts`: Creates a new contact inquiry.
*   `GET /api/contacts`: Retrieves all contact inquiries. (Admin only)
*   `GET /api/contacts/status/{status}`: Retrieves contact inquiries by status. (Admin only)
*   `GET /api/contacts/new`: Retrieves new contact inquiries. (Admin only)
*   `GET /api/contacts/{contactId}`: Retrieves a specific contact inquiry by its ID. (Admin only)
*   `PUT /api/contacts/{contactId}/status`: Updates the status of a contact inquiry. (Admin only)
*   `DELETE /api/contacts/{contactId}`: Deletes a contact inquiry. (Admin only)
*   `GET /api/contacts/count/status/{status}`: Retrieves the count of contact inquiries by status. (Admin only)
*   `GET /api/contacts/count/recent/{days}`: Retrieves the count of recent contact inquiries. (Admin only)

### Projects

*   `GET /api/projects`: Retrieves all projects for the currently authenticated user. (Admin only)
*   `GET /api/projects/featured`: Retrieves the featured projects for the currently authenticated user. (Admin only)
*   `GET /api/projects/{projectId}`: Retrieves a specific project by its ID. (Admin only)
*   `POST /api/projects`: Creates a new project for the currently authenticated user. (Admin only)
*   `PUT /api/projects/{projectId}`: Updates a specific project. (Admin only)
*   `DELETE /api/projects/{projectId}`: Deletes a specific project. (Admin only)
*   `GET /api/projects/user/{userId}`: Retrieves all projects for a specific user. (Admin only)
*   `GET /api/projects/user/{userId}/featured`: Retrieves the featured projects for a specific user. (Admin only)

### Analytics

*   `POST /api/analytics/track`: Tracks a new website visit.
*   `GET /api/analytics/summary`: Retrieves a summary of website analytics. (Admin only)
*   `GET /api/analytics/recent/{days}`: Retrieves recent website visits. (Admin only)
*   `GET /api/analytics/session/{sessionId}`: Retrieves visits for a specific session. (Admin only)
*   `GET /api/analytics/{analyticsId}`: Retrieves a specific analytics record by its ID. (Admin only)
*   `GET /api/analytics/count/total`: Retrieves the total number of visitors. (Admin only)
*   `GET /api/analytics/count/unique`: Retrieves the number of unique visitors. (Admin only)

### Education

*   `GET /api/education`: Retrieves all education entries for the currently authenticated user. (Admin only)
*   `GET /api/education/current`: Retrieves the current education entries for the currently authenticated user. (Admin only)
*   `GET /api/education/{educationId}`: Retrieves a specific education entry by its ID. (Admin only)
*   `POST /api/education`: Creates a new education entry for the currently authenticated user. (Admin only)
*   `PUT /api/education/{educationId}`: Updates a specific education entry. (Admin only)
*   `DELETE /api/education/{educationId}`: Deletes a specific education entry. (Admin only)
*   `GET /api/education/user/{userId}`: Retrieves all education entries for a specific user. (Admin only)
*   `GET /api/education/user/{userId}/current`: Retrieves the current education entries for a specific user. (Admin only)

### Portfolio

*   `GET /api/portfolio/user/{userId}`: Retrieves the public profile of a user.
*   `GET /api/portfolio/user/profile/{userId}`: Retrieves the detailed public profile of a user.
*   `GET /api/portfolio/user/projects/{userId}`: Retrieves the projects of a user.
*   `GET /api/portfolio/user/projects/featured/{userId}`: Retrieves the featured projects of a user.
*   `GET /api/portfolio/user/skills/{userId}`: Retrieves the skills of a user.
*   `GET /api/portfolio/user/skills/featured/{userId}`: Retrieves the featured skills of a user.
*   `GET /api/portfolio/user/education/{userId}`: Retrieves the education of a user.

### Experience

*   `GET /api/experience`: Retrieves all work experiences for the currently authenticated user. (Admin only)
*   `GET /api/experience/current`: Retrieves the current work experiences for the currently authenticated user. (Admin only)
*   `GET /api/experience/{experienceId}`: Retrieves a specific work experience by its ID. (Admin only)
*   `POST /api/experience`: Creates a new work experience for the currently authenticated user. (Admin only)
*   `PUT /api/experience/{experienceId}`: Updates a specific work experience. (Admin only)
*   `DELETE /api/experience/{experienceId}`: Deletes a specific work experience. (Admin only)
*   `GET /api/experience/user/{userId}`: Retrieves all work experiences for a specific user. (Admin only)
*   `GET /api/experience/user/{userId}/current`: Retrieves the current work experiences for a specific user. (Admin only)

## Security

The application uses Spring Security to protect the API endpoints. Most endpoints require administrator privileges (`hasRole('ADMIN')`). Authentication is handled via a session-based approach.

## `pom.xml` Details

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>3.5.7</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.rps</groupId>
	<artifactId>Portfolio</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>Portfolio</name>
	<description>Demo project for Spring Boot</description>
	<url/>
	<licenses>
		<license/>
	</licenses>
	<developers>
		<developer/>
	</developers>
	<scm>
		<connection/>
		<developerConnection/>
		<tag/>
		<url/>
	</scm>
	<properties>
		<java.version>21</java.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<!-- https://mvnrepository.com/artifact/com.mysql/mysql-connector-j -->
		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
			<version>9.4.0</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
			<version>2.2.0</version>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```
    