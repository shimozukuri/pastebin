# pastebin

Pastebin is a service where you can store any text for easy exchange. The service stores notes in the Minio S3 storage.

## Stack
- Java 17
- Maven
- Spring
- JWT
- Liquibase
- Mapstruct
- Lombok
- Swagger
- Mockito
- GitHub Actions
- Docker
- Minio
- Jenkins
- PostreSQL

# API

## Authorization

### `POST` /auth

Authenticate user and return authorization token

Request body:
- `username` - username
- `password` - user password

Response body:
- `token` - authorization token

### `POST` /register

Create new user

Request body:
- `username` - username
- `password` - user password
- `confirmPassword` - password confirmation
- `email` - user email

##  Notes

### `GET` /user/{userId}/notes

Response body:
- return all notes user with id `{userId}`

### `GET` /user/{userId}/note/{noteId}

Response body:
- return notes with id `{noteId}` user with id `{userId}`

### `POST` /user/{userId}/note/new

Create new note for user with id `{userId}`

Request body:
- `title` - title of note
- `content` - content the note

Response body:
- `title` - title of note
- `content` - content the note

### `PUT` /user/{userId}/note/{noteId}/update

Changes the content and title of the note with id `{noteId}` user with id `{userId}`

Request body:
- `title` - title of note
- `content` - content the note

Response body:
- `title` - title of note
- `content` - title of note

### `DELETE` /user/{userId}/note/{noteId}/delete

Delete note with id `{noteId}` user with id `{userId}`

## User

### `GET` /user/{userId}

Return user with id `{userId}`

Response body:
- `id` - user id
- `username` - username
- `password` - user password
- `email` - user email
- `notes` - user notes

### `PUT` /user/{userId}/update

Update user with id `{userId}`

Response body:
- `id` - user id
- `username` - username
- `password` - user password
- `confirmPassword` - password confirmation
- `email` - user email

Response body:
- `id` - user id
- `username` - username
- `password` - user password
- `email` - user email
- `notes` - user notes

### `DELETE` /user/{userId}/delete

Delete user with id `{userId}`