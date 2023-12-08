

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


# Suggestion for improvement

- Emission calculation based on date range
  - calculate total emission data (for employee or whole company),
     based on a date range (by dividing the date range to weeks)
  

- Consider implementation of API for register new user for a company
  (to support multi-user possible for user to company link)


- Error response messages using i18n


- Test implementation for error scenarios 
  - Not Authorized in secured endpoints
  - Corrupt CSV data upload
  - Wrong username/password in login


- Use an external API for emission calculations, in order to find the latest
    emission rate for vehicles


- Average weekly mileage for employees should be updated, based on a tracking app,
    and it can be calculated and updated daily (in a scheduled job), or updates near real time
    by consuming events from the tracking app (For example by using Kafka)