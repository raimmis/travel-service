# Travel Service Rest API

Small Rest API service to calculate ticket prices
Rest API is running in port 8080
API base path is http://localhost:8080/api

## Required tools
* Maven
* Java

## Building application
`mvn clean install`

## Running application
`java -jar target/travel-service-0.0.1-SNAPSHOT.jar`

## Testing api
 Rest API specification can be read and API can be tested through Swagger UI (http://localhost:8080/api/swagger-ui.html)
 
## Demo users
### Admin User:
Username: admin,
Password: admin 
 
## Improvement TODOs
1) Replace simple hardcoded base-auth with oAuth2 token based authentication or DB based users  
2) Improve/Refactor unit tests and test coverage, write some spring boot integration tests 
3) Replace mock external service endpoints with real endpoints