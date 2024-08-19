# pastebin
Pastebin is a service where you can store any text for easy exchange.

### Stack
- Java 17
- Maven
- Spring
- JWT
- Liquibase
- Mapstruct
- Swagger
- Mockito
- GitHub Actions
- Docker
- Minio
- Jenkins
- PostreSQL

## Authorization

### `POST` [/auth](http://localhost:8080/auth)

Authenticate user and return authorization token

Request body:
- `username` - username
- `password` - user password

Response body:
- `token` - authorization token

### `POST` [/register](http://localhost:8080/register)

Create new user

Request body:
- `username` - username
- `password` - user password
- `confirmPassword` - password confirmation
- `email` - user email

##  Notes

### `GET` [/user/{userId}/notes](http://localhost:8080/user/1/notes)

Response body:
- return all notes user with id `{userId}`

### `GET` [/user/{userId}/note/{noteId}](http://localhost:8080/user/1/note/1)

Response body:
- return notes with id `{noteId}` user with id `{userId}`

### `POST` [/user/{userId}/note/new](http://localhost:8080/user/1/new)

Create new note for user with id `{userId}`

Request body:
- `title` - title of note
- `content` - content the note

Response body:
- `title` - title of note
- `content` - content the note

### `PUT` [/user/{userId}/note/{noteId}/update](http://localhost:8080/user/1/note/1)

Changes the content and title of the note with id `{noteId}` user with id `{userId}`

Request body:
- `title` - title of note
- `content` - content the note

Response body:
- `title` - title of note
- `content` - title of note

### `DELETE` [/user/{userId}/note/{noteId}/delete](http://localhost:8080/user/1/note/1)

Delete note with id `{noteId}` user with id `{userId}`

## User

### `GET` [/user/{userId}](http://localhost:8080/user/1)

Return user with id `{userId}`

Response body:
- `id` - user id
- `username` - username
- `password` - user password
- `email` - user email
- `notes` - user notes

### `PUT` [/user/{userId}/update](http://localhost:8080/user/1)

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

### `DELETE` [/user/{userId}/delete](http://localhost:8080/user/1)

Delete user with id {userId}