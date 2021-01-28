# Sample OpenFeign Initialization Error Project

This project demonstrates a Spring Feign client lazy initialization error, when
using ```CompletableFuture.supplyAsync()``` to trigger Feign client initialization.

## Project Contents
The project contains a [feign client](src/main/kotlin/de/digitalfrontiers/spring/build/image/SampleFeignClient.kt)
that fetches http://www.google.com. This client is used directly by a
[rest controller](src/main/kotlin/de/digitalfrontiers/spring/build/image/SampleController.kt).
By design, Feign clients in Spring are lazily initialized, thus leaving initialization
to the first caller. In this case, the calling rest controller uses ```CompletableFuture.supplyAsync()```
to handle the outbound Feign call asynchronously.

## Project Build
The project can be built using:
```
./gradlew clean bootBuildImage
```

### Error Behavior
The Spring Boot application contained in this project can be tested successfully, as
follows:
```
./gradlew clean bootRun
curl http://localhost:8080/api/sample
```
This should yield the output from http://www.google.com, fetched by the Feign client.

However, when running the very same application from within Docker, as follows
```
./gradlew clean bootBuildImage
docker-compose up
curl http://localhost:8080/api/sample
```
the Feign client initialization will fail with
```
java.lang.ClassNotFoundException: org.springframework.boot.autoconfigure.condition.OnPropertyCondition
```
When looking at the stack-trace, it is obvious that the ```ForkJoinWorkerThread``` in a Docker
image built using the Spring Boot Build Image plugin seems to cause this error when
trying to lazily initialize the Feign client. The error seems to be strongly related
to the Spring Boot Build Image, since using `jib` instead, does not cause this error.

The same is true for running the Docker image in certain Kubernetes environments, although
not all environments seem to be affected.
