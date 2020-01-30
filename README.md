# vcalFileUpload
- Spring Boot
- Java 11
- Maven

## About
vcalFileUpload is a file upload service made with Spring Boot. Enables file upload
through a REST API. Two different upload options: sequential and concurrent (parallel).

## Project requirements
- RestController
- Max. 8 threads.
- 100 parallel uploads, 50MB in size
- Parallel upload can not allow same-named files, sequential overrides previous.

## API docs

### upload sequential
- http://localhost:9090/api/v1/upload/sequential

- accepts multiple files, stores files one by one, using a for loop.

### upload concurrent (parallel)
- http://localhost:9090/api/v1/upload/concurrent

- accepts multiple files, using threads it stores multiple files at once.

### track progress
- http://localhost:9090/api/v1/upload/progress

### track duration
- http://localhost:9090/api/v1/upload/duration