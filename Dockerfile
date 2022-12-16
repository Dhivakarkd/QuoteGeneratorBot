FROM openjdk:8-jdk-alpine

LABEL org.opencontainers.image.source=https://github.com/Dhivakarkd/QuoteGeneratorBot
LABEL org.opencontainers.image.description="A TelegramBot which sents motivational Quotes for every registered user everyday , Be motivated every day by a new Quote from TelegramBot"
LABEL org.opencontainers.image.licenses=MIT

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY build/libs/QuoteGeneratorBot-1.0.0.jar  app.jar
ENTRYPOINT java $JAVA_OPTS -jar /app.jar