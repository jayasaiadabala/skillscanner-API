# SkillScanner API

SkillScanner API is a backend service built with **Spring Boot** that powers the SkillScanner platform. It provides APIs to manage users, service providers (partners), skills, and service discovery, helping bridge the gap between skilled professionals and customers who need reliable services.

This API is designed to be scalable, secure, and easy to integrate with web or mobile clients.

---

## 🚀 Features

* User and partner registration
* Skill and service management
* Partner discovery based on skills
* Modular and layered architecture
* Built with Spring Boot and Maven
* Docker support for easy deployment

---

## 🌐 Live Demo

* 👉 **Live Application:** [https://skillscanner-jayasaiadabala.vercel.app/](https://skillscanner-jayasaiadabala.vercel.app/)

* 🔗 **Frontend Repository:** [https://github.com/jayasaiadabala/skillscanner-frontend](https://github.com/jayasaiadabala/skillscanner-frontend)

The frontend is deployed on **Vercel** and communicates with this Spring Boot backend API.

---

## 🛠 Tech Stack

* **Language:** Java
* **Framework:** Spring Boot
* **Build Tool:** Maven
* **Database:** **NeonDB (PostgreSQL)**
* **ORM:** Spring Data JPA / Hibernate
* **Containerization:** Docker
* **Backend Deployment:** **Render**
* **Frontend Deployment:** **Vercel**

---

## 📂 Project Structure

```
skillscanner-API
├── src/main/java
│   └── com/skillscanner
│       ├── controller
│       ├── service
│       ├── repository
│       └── model
├── src/main/resources
│   ├── application.properties
│   └── static
├── Dockerfile
├── pom.xml
└── README.md
```

---

## ⚙️ Setup & Installation

### Prerequisites

* Java 17+
* Maven 3.8+
* MySQL
* Docker (optional)

### Clone the Repository

```bash
git clone https://github.com/jayasaiadabala/skillscanner-API.git
cd skillscanner-API
```

### Configure Database

Update `application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/skillscanner
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### Run the Application

```bash
mvn spring-boot:run
```

The API will start at:

```
http://localhost:8080
```

---

## 🐳 Run with Docker

Build the Docker image:

```bash
docker build -t skillscanner-api .
```

Run the container:

```bash
docker run -p 8080:8080 skillscanner-api
```

---

## 📌 API Endpoints

| Method | Endpoint                                  | Description                         |
| ------ | ----------------------------------------- | ----------------------------------- |
| POST   | /register                                 | Register a new user                 |
| POST   | /sendOtp/{email}                          | Send OTP to user email              |
| POST   | /sendResetOtp/{email}                     | Send OTP for password reset         |
| POST   | /verifyOtp/{email}/{otp}                  | Verify OTP for user                 |
| GET    | /allusers                                 | Get all registered users            |
| GET    | /login/{email}/{password}                 | Login user using email and password |
| GET    | /user/{email}                             | Get user details by email           |
| PUT    | /update                                   | Update user details                 |
| DELETE | /delete/{email}                           | Delete user by email                |
| GET    | /mailToPartner/{partnerGmail}/{userGmail} | Send email from user to partner     |

---

## 🔐 Security

* Input validation
* Layered architecture for better security
* Can be extended with Spring Security and JWT authentication

---

## 🤝 Contributing

Contributions are welcome!

1. Fork the repository
2. Create a new branch (`feature/your-feature-name`)
3. Commit your changes
4. Push to your branch
5. Open a Pull Request

---

## 📄 License

**MIT License** © 2025 SkillScanner Project.

---

## 👨‍💻 Author

**Jaya Sai Adabala**
GitHub: [jayasaiadabala](https://github.com/jayasaiadabala)

---

> *At SkillScanner, we believe every skill is worthy — and this API is the backbone that makes those skills discoverable.*
