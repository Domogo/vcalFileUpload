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

The service accepts file uploads up to 50MB in size. Upload duration and progress are tracked and stored
in an in-memory database. It's possible to see the duration of every finished upload in a session and see
progress of currently uploading files ( NOTE: it's hard to catch locally as the upload spead is quite fast,
which will result in an empty response. To see the actual progress you can spam the service with upload
requests ). There can be up to a 100 active uploads, any requests made while the capacity is full will be
turned down. Further more, there can be no two (or more) file uploading at the same time with the same
name, but if the conflicting file has finished uploading, it can be overridden with another upload
using the same file name.

## API docs

- (Images from Postman)

### upload files

- POST: `http://localhost:9090/api/v1/upload`

- endpoint for uploading a file.
- set `XUploads-File` header with a value that will be the uploaded file name.

![UploadHeader](readmeImages/uploadHeader.png)

- Request body should contain a file under the key `file`.

![UploadBody](readmeImages/uploadBody.png)

### track progress

- GET: `http://localhost:9090/api/v1/upload/progress`

- displays the upload progress of files being uploaded at the moment.

![UploadProgress](readmeImages/uploadProgress.png)

- NOTE: The upload progress is hard to catch locally. The upload speed is very and the files finish uploading within miliseconds.
The above image is using altered data to show the funcionality of the endpoint.

### track duration

- GET: `http://localhost:9090/api/v1/upload/duration`

- displays the duration it took to upload files. Only displays those that have completed the upload process.

![UploadDuration](readmeImages/uploadDuration.png)

## Integration Tests

### Parallel upload limit

- fileUpload_whenActiveUploadLimit_thenStatus429
- When there's a 100 active uploads, return HTTP code 429. (Too many requests).

### Upload size limit (50MB)

- fileUpload_whenFileSizeTooBig_thenStatus413
- When file is over 50MB, return HTTP code 413 (Payload too large).
- NOTE: The test is failing because the MockMvc is not using the MultipartResolver so it doesn't
catch the exception from MultipartResolver - live testing catches it.

### When multiple parallel uploads with the same name

- fileUpload_whenFileWithThatNameCurrentlyUploading_thenStatus409
- when there's a name conflict, return HTTP code 409 (Conflict).