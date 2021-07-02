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

## Run as standalone
##### Pre-requisites

- Make sure postgres is up & running.
```bash
docker run -p 5432:5432 -d -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=ufs_pad --name postgres postgres:9.6.12
java -jar ctms-api/target/ctms-api.jar
```

## Run as docker container
```bash
docker network create ufs
docker run -p 5432:5432 -d -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=ufs_pad --network=ufs --name postgres_ufs postgres:9.6.12
docker run -p 8800:8800 -d -e pg_host=postgres_ufs --network=ufs --name ufs bilal0606/ufs-api:2020.1.1
```

## Run on kubernetes
```bash
kubectl apply -f postgres-deployment.yaml,deployment.yaml
```

## Modules

- ctms-api        - RESTful service implementation.
- ctms-model      - API definition for microservices.

## Swagger

```thymeleafurlexpressions
 http://localhost:8800/ufs-api/swagger-ui/index.html?configUrl=/ufs-api/v3/api-docs/swagger-config#/
```
