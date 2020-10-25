
<h1 align="center">
  <a href="#">
    myretail-api
  </a>
</h1>

<p align="center">
  Build on SpringBoot
</p>

## Build App

```
mvn clean install
```

## Run App

```
java -jar retail-products-api-0.0.1-SNAPSHOT.jar
http://localhost:8080/swagger-ui.html
```

## Surefire Report

```
mvn surefire-report:report
```

## Docker

```
docker build -t {DOCKER_ACCOUNT}/myretail .
docker run -p 8080:8080 {DOCKER_ACCOUNT}/myretail
```
