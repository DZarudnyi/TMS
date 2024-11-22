# Task Management System
TMS is an API that gives user all the backend capabilities of a regular task management system.



## Used technologies
Java 17

MySQL 8

Spring Boot 3.1.3

Spring Data Jpa

Spring Security

JWT

Docker

Lombok

Mapstruct

Dropbox 6.0

## Features
**Soft delete** - any time you use delete endpoint it doesn't remove the record from the database, but changes record’s field ‘is_deleted’ to true, so data can be easily restored

**Docker support** - project has all required files to create docker images and run it in a docker container and can easily be modified to meet your needs

**JWT authorization** - after authorization JWT token will be used to make user experience secure and convenient

**Role-based access control (RBAC)** - new users can gain necessary permissions automatically, while “dangerous” zones will remain secure and accessible only for selected group of users

**Attachment support** - program allows users to upload and download files by utilizing Dropbox API

## Future features
Notification support

Searching and filtering for tasks and projects