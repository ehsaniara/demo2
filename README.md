# Spring Boot JPA example


Make sure your docker desktop is running (https://www.docker.com/get-started/)

> Note: review the following link for docker license https://www.docker.com/pricing/ base on your workstation.

### Run Local Postgres

```shell
docker-compose up -d
```

docker compose file [docker-compose.yml](docker-compose.yml) has provided on this repo. 

### Test

```shell
./gradlew test
```