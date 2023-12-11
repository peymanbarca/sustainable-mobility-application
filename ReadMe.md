

## A Sustainable Mobility Management Service for companies and their employees


### This project implemented with Kotlin 1.9, Spring Boot 3.0.1 and maven

#### This project only has a default environment. In order to start the project, first a local postgresql DB is needed.
Run the following in order to launch a containerized local postgresql DB

    cd src/main/resources/db/ && docker-compose up -d
    
#### After that, by running the project, Hibernate DDL is responsible for create tables.


### Swagger ui
    http://localhost:8080/swagger-ui/index.html#

### Run Tests
    mvn test 

### Build the project
    mvn clean && mvn package

### Build the docker image
    docker build -t mobility-management-service:staging .


# Suggestion for improvement

- Implementation of Refresh token mechanism


- We can retrieve vehicle types and their emission rates from user in our upload service.
 or we can allow for persist an employee, which the data for its vehicle type,
    doesn't exist in our system. But during emission calculation, we can't compute for that specific employee,
    and we should first retrieve its vehicle emission data (emission per mile), from external resources.


- Emission calculation based on date range
  - calculate total emission data (for employee or whole company),
     based on a date range (by dividing the date range to weeks)
  

- Consider implementation of API for register new user for an existed company
  (to support multi-user possible for user to company link)


- Error response messages using i18n


- During test implementations, we can also use mocking, to mock the current user,
  instead of really login with a constant test user.
- We should also perform test implementation for error scenarios, such as: 
  - Corrupt CSV data upload


- Using test containers for running tests, (a complete new postgresql db)

- Using Spring Rest Docs to automatically document the tests


- Use an external API for emission calculations, in order to find the latest
    emission rate for vehicles. We should fetch information for all available vehicle types, from external API, regularly,
    and cache them (By using Redis for example)


- Average weekly mileage for employees should be updated, based on a tracking app,
    and it can be calculated and updated daily (in a scheduled job), or updates near real time
    by consuming events from the tracking app (For example by using Kafka),
    Also we can cache the current value of average weekly mileage for an employee, with desired TTL, and use that for 
    calculation purposes.
