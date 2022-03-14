## Internet store template

### Stack

#### Backend

* Spring
    * S. boot web for rest api
    * S. boot jooq to setup jOOQ
    * S. actuator to manage app via REST in f.e. Prometheus
    * S. security to handle authentication and secure endpoints
* JWT to operate authorization tokens
* Liquibase to keep code and DB consistency
* jOOQ to keep DB and code consistency and simple DAO way to do SQL
* Swagger oAPI to represent and formalize API of app
* Mapstruct to generate mappers for models in compile-time

#### Frontend

* Typescript
* React
* MUI
* Axios

### Setup

1. Start `./compose/postgre-compose.yml` for DB app instance
2. Start `./gradlew :backend:bootRun` for backend instance
3. Start `./gradlew :frontend:npm_start` for frontend instance

