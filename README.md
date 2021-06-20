# User Fleet Service (UFS)

UFS handles user & their vehicle registration. This service manages the user profile and provides available charging
station depending on the vehicle location.

# Installation

## Prerequisites

* [Java](https://openjdk.java.net/) v11
* [Maven](https://maven.apache.org/) v3.3
* [Docker](https://docs.docker.com/) v20.10.6
* [Postgresql](https://www.postgresql.org/) v9.6.21+

## Build
* To run maven build with junit test coverage
```bash
mvn clean install
```
* To run maven build along with `integration-test` mode
```bash
mvn clean install -P integration-test
```

## Run

```bash
java -jar ctms-api/target/ctms-api.jar
```

## Modules

- ctms-api        - RESTful service implementation.
- ctms-model      - API definition for microservices.

## Swagger

```thymeleafurlexpressions
 http://localhost:8800/ufs-api/swagger-ui/index.html?configUrl=/ufs-api/v3/api-docs/swagger-config#/
```
