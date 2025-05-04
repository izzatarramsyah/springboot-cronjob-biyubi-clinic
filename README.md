# Biyubi Clinic Growth Monitoring App (Cron Job Scheduler)

A backend web application designed to send automated notifications based on the growth monitoring data for children aged 0-2 years old, according to **WHO Z Score** guidelines. The application leverages **cron jobs** for scheduling notifications related to growth checkups, immunization schedules, and other health-related reminders.

---

## 📦 Tech Stack

- **Java Spring Boot 8**
- **Maven** (for dependency management and build automation)
- **PostgreSQL** (for database management)

---

## 🛠️ Features

- 📅 **Cron Job Scheduler**: Schedule and automate sending notifications for growth checkups and immunization reminders.
- 🔔 **Notifications**: Send email & whatsapp

---

## 🚀 Getting Started

### Prerequisites

- **Java 8+** for building and running the application
- **Apache Maven (mvn)** for dependency management and building the project
- **PostgreSQL** for database storage

### Installation

1. **Clone the repository:**
    ```bash
    git clone https://github.com/izzatarramsyah/springboot-cronjob-biyubi-clinic
    cd biyubi-clinic-growth-monitoring-backend
    ```

2. **Install dependencies** using Maven:
    ```bash
    mvn clean install
    ```

3. **Configure PostgreSQL**: Update the `application.properties` file with your PostgreSQL database credentials.

4. **Run the application**:
    ```bash
    mvn spring-boot:run
    ```

5. **Access the application**: The backend service should now be running at `http://localhost:8080`.

---
