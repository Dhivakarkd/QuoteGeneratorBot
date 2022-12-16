LABEL org.opencontainers.image.source=https://github.com/Dhivakarkd/QuoteGeneratorBot
LABEL org.opencontainers.image.description="A TelegramBot which sents motivational Quotes for every registered user everyday , Be motivated every day by a new Quote from TelegramBot"
LABEL org.opencontainers.image.licenses=MIT
FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY build/libs/*.jar  app.jar
ENTRYPOINT java $JAVA_OPTS -jar /app.jar