FROM gradle:jdk8 as build
USER root
WORKDIR /home/gradle
COPY . .
RUN ./gradlew war
EXPOSE 8080
CMD ["./gradlew", "bootRun"]
