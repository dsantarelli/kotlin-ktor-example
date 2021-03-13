# Kotlin Ktor Example App

This codebase was created to demonstrate a REST API built with **Kotlin + Ktor + Kodein + Exposed** including CRUD operations, routing, dependency config and more.

The application was built with:

  - [Kotlin](https://github.com/JetBrains/kotlin) as programming language
  - [Ktor](https://github.com/ktorio/ktor) as web framework
  - [Kodein](https://github.com/Kodein-Framework/Kodein-DI) as dependency injection framework
  - [Jackson](https://github.com/FasterXML/jackson-module-kotlin) as data bind serialization/deserialization    
  - [MapStruct](https://mapstruct.org/) as mapper between types (annotation processor)
  - [HikariCP](https://github.com/brettwooldridge/HikariCP) as datasource to abstract driver implementation
  - [H2](https://github.com/h2database/h2database) as database
  - [Exposed](https://github.com/JetBrains/Exposed) as ORM framework
  - [Netty](https://netty.io/) as embedded web
  
Testing:
 
  - [JUnit 5](https://junit.org/junit5/)
  - [ktor-web-test](https://ktor.io/docs/testing.html) to call endpoints in the integration tests
  - [jacoco](https://www.eclemma.org/jacoco/) as code coverage library
 
 
### Project Structure
      + config/
          All app setups. Ktor, Kodein, Database etc.
		  
      + dao/
	      Persistence layer and tables definitions
		
      + handlers/
          Request handling business logic
		  
      + controllers/
          Classes and methods to map routes to request handlers
		  
      - App.kt <- The main class

## Getting started

### Requirements

* [Gradle](https://gradle.org/)
* [Java 11](https://www.oracle.com/it/java/technologies/javase-jdk11-downloads.html)

The web is configured to start on port [8080](http://localhost:8080).

Build:
> ./gradlew clean build

Start the application:
> ./gradlew run

Test:
> ./gradlew test
