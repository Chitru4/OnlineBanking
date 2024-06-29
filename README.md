# Online Banking Application

![Project Banner](![image](https://github.com/Chitru4/OnlineBanking/assets/100341402/9792aec5-3246-4200-940d-ec69daee1512))

## Introduction

Welcome to the Online Banking Application! This project aims to provide users with a secure and user-friendly platform to manage their bank accounts, perform transactions, and schedule automatic payments. Built using Spring Boot, MySQL, and Thymeleaf, this application follows a robust and scalable architecture to ensure smooth and efficient banking operations.

## Features

- User Registration and Authentication
- Account Management
- Fund Transfers
- Scheduled Transactions
- Transaction History
- Responsive Design for Mobile and Desktop

## Technologies Used

- **Frontend**: Thymeleaf
- **Backend**: Spring Boot, Spring MVC, Spring Data JPA
- **Database**: MySQL
- **Testing**: JUnit
- **Build Tool**: Maven

## Project Structure

```plaintext
.
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example.onlinebanking
│   │   │           ├── controller
│   │   │           ├── service
│   │   │           ├── model
│   │   │           ├── repository
│   │   │           └── configuration
│   │   └── resources
│   │       ├── static
│   │       └── templates
│   └── test
│       └── java
│           └── com
│               └── example.onlinebanking
├── pom.xml
└── README.md
```

## Block Diagram

![Block Diagram](![banking_app_diagram](https://github.com/Chitru4/OnlineBanking/assets/100341402/0b0c8aad-29b3-4933-b7b6-67dad2c6a6c2))

## Setup and Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Chitru4/OnlineBanking.git
   cd online-banking-app
   ```

2. **Configure the database**
   - Update the `application.properties` file with your MySQL database credentials.

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

## Usage

- **Access the application**
  - Open your web browser and navigate to `http://localhost:8080`

- **Register a new user**
  - Go to the registration page and fill in your details.

- **Login**
  - Use your registered credentials to log in.

- **Manage Accounts**
  - Create and manage your bank accounts.

- **Perform Transactions**
  - Transfer funds between accounts and view transaction history.

- **Setup Scheduled Transactions**
  - Schedule automatic payments and manage them.

## Testing

- **Run unit tests**
  ```bash
  mvn test
  ```

## Contribution

Contributions are welcome! Please fork the repository and create a pull request with your changes.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For any questions or inquiries, please contact [chitraksh24@gmail.com](mailto:chitraksh24@gmail.com).

---

![Footer Image](![image](https://github.com/Chitru4/OnlineBanking/assets/100341402/f063508b-4aac-46ca-ac0c-47e339ea5d8c)



