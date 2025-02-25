FROM gradle:8.12.1-jdk17 AS builder

ENV APP_HOME=/carrent-api
WORKDIR ${APP_HOME}

COPY build.gradle settings.gradle gradlew ${APP_HOME}
COPY . .

RUN gradle clean bootJar --no-daemon

FROM openjdk:17-alpine AS runtime

ENV APP_HOME=/carrent-api
ENV JAR_NAME=carrent-api-0.0.1-SNAPSHOT.jar
WORKDIR ${APP_HOME}

COPY --from=BUILDER ${APP_HOME}/build/libs/${JAR_NAME} ${APP_HOME}

CMD java -jar ${JAR_NAME}