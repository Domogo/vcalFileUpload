# vcalFileUpload

- Spring Boot
- Java 11
- Maven

## About

vcalFileUpload is a file upload service made with Spring Boot. Enables file upload
through a REST API. Two different upload options: sequential and concurrent (parallel).

## Project requirements

- RestController
- Server can have max. 8 threads.
- accept 100 parallel uploads at any given time, each up to 50MB in size
- Parallel upload can not allow same-named files, sequential overrides existing files with the same name.

## Details

- files are stored in your (OS-specific) TMP dir.
- in-memory DB (hibernate) has a File Model that creates a record on upload.

## API docs

### upload files

- POST: `http://localhost:9090/api/v1/upload`

### upload multiple

- POST: `http://localhost:9090/api/v1/upload/multiple`

### track progress

- GET: `http://localhost:9090/api/v1/upload/progress`

### track duration

- GET: `http://localhost:9090/api/v1/upload/duration`
- For finished uploads displays the time it took to upload them.
