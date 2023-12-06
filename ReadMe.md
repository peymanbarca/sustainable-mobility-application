

## A Sustainable Mobility Management Service for companies and their employees


### This project implemented with Kotlin 1.9, Spring Boot 3.0.1 and maven

#### This project only has a default environment. In order to start the project, first a local postgreSQL DB is needed. Run the following in order to launch a containerized local postgresql DB
    cd src/main/resources/db/ && docker-compose up -d
    
#### After that, by running the project, Hibernate DDL is responsible for create tables.
    
### Services


### Swagger ui
    http://localhost:8080/swagger-ui/index.html#

### Run Tests
    mvn test 

### Build the project
    mvn clean && mvn package

### Build the docker image
    docker build -t mobility-management-service:staging .