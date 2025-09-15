A Spring Boot REST API backend to support the Assistive Communication App, designed to help people with speech disabilities communicate.

🚀 Features
Upload Audio: Save user-generated audio (from speech-to-text) into MySQL.
Retrieve Audio: Fetch stored recordings by ID.
REST API: Easy integration with the React frontend.

🛠️ Tech Stack

Backend Framework: Spring Boot (Java 17)
Database: MySQL (JPA/Hibernate)
Build Tool: Maven
API Testing: Postman / cURL

⚙️ Installation & Setup
1. Clone the repository
git clone https://github.com/username/assistive-comm-backend.git
cd assistive-comm-backend

2. Configure Database

Edit src/main/resources/application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/assistive_comm
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

3. Run the app
mvn spring-boot:run (For Windows)
👉 App runs at http://localhost:8080

