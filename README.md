# About
This is the member-service project, developed according to the declared requirements.

The main operations supported by the service are:
* adding new member
* getting member
* updating member
* deleting member
* getting list of all the created members

# Compliance with Acceptance criteria
* RESTful web-service with all required member-fields.  
* Depending on headers in request accepts - JSON/XML, reponse - JSON/XML.  
* Error handling via exception handler - `ControllerExceptionHandler` with `NOT FOUND` and `BAD REQUEST` statuses depending on circumstances  
* JDK 8 is used and maven.  
* Artifact - executable jar (fat jar) with all dependencies inside of it
* Data storage - H2 embedded + database schema changes management via Liquibase
* Tomcat server (embedded)
* Ability of creating docker images via dockerfile (unfortunately I don`t have my personal docker registry)    
* Spring Boot, Apache Commons, Liquibase, Spring Data, Lombok  

# Build, deploy and run
Clone this repository:  
`https://github.com/ChaikaAM/members-service`

The easiest way to run the project is :  
`mvn clean spring-boot:run`  

But if you need executable jar just use:
`mvn clean package` and the artifact will be available in `target/members-0.0.1-SNAPSHOT.jar`  
To run it just use common `java -jar target/members-0.0.1-SNAPSHOT.jar`

After you have built artifact you can do in docker:
* create image of application `docker build -t members_image`
* create container of it `docker create --name members -p 8000:8000 members_image`  
* run it `docker start members`  

Ensure that app is running by visiting swagger page 127.0.0.1:8000/swagger-ui.html

# REST Api docs
Endpoints, requests and responses are described at swagger - 127.0.0.1:8000/swagger-ui.html 

# How to use
To switch between json and xml just set `Content-Type` and `Accept` correctly 
It is easy to test all endpoints with postman via collection https://www.getpostman.com/collections/39ab5067ec9d00d00d14 , which contains JSON and XML requests 

# Testing strategy

There were no requirements if absolutely all the lines of code should be covered by tests, 
so I did what I could for not so big period of time 

## Unit tests
Service layer is covered by unit tests, including exceptional cases

## Consumer driven contract testing  
I was going to use `Spring Cloud Contract` but unfortunately I had not enough time to implement that...  
But I wrote the API test - `MembersApiTest`, btw that was the first test in this project
and it helped me a lot to develop this App, so we can say that TDD has been also used

## Stress test results
Scenario is very simple - sending requests to create new members for N seconds and 
calculating simple stats after that.   
Results on my local machine:  
`1 secs stress : 103  requests have been handled, 103 requests per secs, 103 members created per sec`  
`3 secs stress : 922  requests have been handled, 307 requests per secs, 307 members created per sec`  
`5 secs stress : 2541 requests have been handled, 508 requests per secs, 508 members created per sec`  
`10 secs stress: 7106 requests have been handled, 710 requests per secs, 710 members created per sec`