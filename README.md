# Delivery Options API

This Spring Boot application provides a REST API for customers to choose their preferred delivery methods and specific time slots for their deliveries. It's designed to accommodate real-time booking scenarios with support for multiple delivery methods.

## Features

- **Choose Delivery Method**: Customers can select from DRIVE, DELIVERY, DELIVERY_TODAY, and DELIVERY_ASAP.
- **Book Time Slots**: Customers can choose specific time slots for the selected delivery method. Time slots availability updates in real-time and is specific to each delivery method.
- **Swagger Documentation**: Fully documented API accessible via Swagger UI.
- **Security**: Secured endpoints using Spring Security.
- **In-Memory Database**: Utilizes H2 database for persistence, facilitating quick setup and testing.

## Technologies

- Spring Boot 3.x
- Spring Security
- H2 Database
- Swagger UI
- Java JDK 21
- Docker (for containerization and deployment)
- GitHub Actions (for CI/CD)

## Setup

### Prerequisites

- Java 21 or above
- Maven
- Docker (optional for containerization)

### Running Locally

Clone the repository and navigate to the project directory:

```bash
git clone https://github.com/s-racem/DriveAndDeliver.git
cd DriveAndDeliver
