# JobOffers - Backend
The JobOffers backend is a secure platform that allows users to
to access job offers by authenticating themselves.
It also allows users to create their own job offers.
offers. It also contains a scheduler that updates
offers from an external API every 3 hours.
In addition, the application uses Redis caching for
optimised performance and faster data retrieval.

## Architecture
![Architecture](./architecture/architecture-v2.png)


## Endpoints

| Endpoint           | Method | Request                                     | Response                 | Function                                  |
|--------------------|--------|---------------------------------------------|--------------------------|-------------------------------------------|
| `/register`        | `POST` | JSON BODY(username, password)               | JSON (boolean isCreated) | create new user                           |
| `/token`           | `POST` | JSON BODY(username, password)               | JSON (token, username)   | Returns user data                         |
| `/offers`          | `POST` | JSON BODY(title, company, salary, offerUrl) | JSON (offer)             | Creates new offer                         |
| `/offers/{id}`     | `GET`  | Path variable id                            | JSON (offer)             | Print selected offer                      |
| `/offers`          | `GET`  | -                                           | JSON (list of offers)    | Print all offers


## Technologies

![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Java 17](https://img.shields.io/badge/Java_17-007396?style=for-the-badge&logo=java&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Wiremock](https://img.shields.io/badge/Wiremock-EE3124?style=for-the-badge&logo=swagger&logoColor=white)
![Log4j2](https://img.shields.io/badge/Log4j2-9B7E3E?style=for-the-badge&logo=apache&logoColor=white)
![Testcontainers](https://img.shields.io/badge/Testcontainers-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![RestTemplate](https://img.shields.io/badge/RestTemplate-007396?style=for-the-badge&logo=spring&logoColor=white)
![Awaitility](https://img.shields.io/badge/Awaitility-26A65B?style=for-the-badge&logoColor=white)
![JUnit5](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=java&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-25A162?style=for-the-badge&logo=java&logoColor=white)
![AssertJ](https://img.shields.io/badge/AssertJ-26A65B?style=for-the-badge&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-007396?style=for-the-badge&logo=java&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)
![GitLab](https://img.shields.io/badge/GitLab-FCA121?style=for-the-badge&logo=gitlab&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)


## How to build the project on your own
1. Clone this repository
```shell
git clone https://github.com/FuuKowatty/Job-Offers.git
```
2. Go to the folder with cloned repository
3. Run docker compose
```shell
docker compose up
```
4. Run the application
