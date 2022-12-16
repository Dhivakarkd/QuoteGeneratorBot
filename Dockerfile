FROM gradle:4.7.0-jdk8-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:8-jdk-alpine

LABEL org.opencontainers.image.source=https://github.com/Dhivakarkd/QuoteGeneratorBot
LABEL org.opencontainers.image.description="A TelegramBot which sents motivational Quotes for every registered user everyday , Be motivated every day by a new Quote from TelegramBot"
LABEL org.opencontainers.image.licenses=MIT

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY --from=build /home/gradle/src/build/libs/*.jar  app.jar
ENTRYPOINT java $JAVA_OPTS -jar /app.jar